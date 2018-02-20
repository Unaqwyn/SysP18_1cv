package motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import java.util.Random;

public class ServoA extends Task {
	private final int testChannel = 1;
	private final boolean useTPUA = true;
	// pwmPeriod in TimeBase Unit (50’000 ns)
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;

	public static int highTime = 1200000 / TPU_PWM.tpuTimeBase;
	public static int newHighTime = 2200000 / TPU_PWM.tpuTimeBase;
	public static int max = 2200000 / TPU_PWM.tpuTimeBase;
	public static int min = 1200000 / TPU_PWM.tpuTimeBase;
	public static int schrittweite = (max - min) / 100;

	private Random random;
	private boolean vibration = false;

	private static TPU_PWM pwm;
	public boolean auf = true;

	public ServoA() {
		pwm = new TPU_PWM(useTPUA, testChannel, pwmPeriod, highTime);
		period = 100; // Periodenlaenge des Tasks in ms
		Task.install(this);
		random = new Random();
	}

	
	//0<=x/y<=1, y!=0
	public void newPos(int x, int y) {
		highTime = (int) min + (x * (max-min) / y);
		pwm.update(highTime);
	}

	public void vibration() {
		int mod = random.nextInt();
		mod--;
		if (highTime >= max) {
			highTime = (int) ((min + max) * 0.5);
		} else {
			highTime += (schrittweite * mod);
		}
		pwm.update(highTime);
	}

	public void toggleVibration() {
		vibration = !vibration;
	}
	
	public void min()
	{
		pwm.update(min);
	}

	public void max()
	{
		pwm.update(max);
	}
	
	static { // Task Initialisierung
		new ServoA();
	}
}
