package motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import java.util.Random;

public class Servo extends Task {
	private final boolean useTPUA = true;
	// pwmPeriod in TimeBase Unit (50’000 ns)
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;

	public static int highTime = 1200000 / TPU_PWM.tpuTimeBase;
	public static int newHighTime = 2200000 / TPU_PWM.tpuTimeBase;
	public static int max = 2200000 / TPU_PWM.tpuTimeBase;
	public static int min = 1200000 / TPU_PWM.tpuTimeBase;
	public static int minPos = 1609000 / TPU_PWM.tpuTimeBase;
	public static int maxPos = 1701000 / TPU_PWM.tpuTimeBase;

	private Random random;

	private static TPU_PWM pwm;
	public boolean auf = true;

	public Servo(int testChannel) {
		pwm = new TPU_PWM(useTPUA, testChannel, pwmPeriod, highTime);
		period = 100; // Periodenlaenge des Tasks in ms
		Task.install(this);
		random = new Random();
	}

	// 0<=x/y<=1, y!=0
	public void newPos(double x, double y) {
		highTime = (int) (min + (x * (max - min) / y));
		pwm.update(highTime);
	}

	public void vibration() {
		int schrittweite = (maxPos - minPos) / 50;
		int mod = random.nextInt(20);
		mod-=10;
		if (highTime >= maxPos) {
			highTime = (int) ((minPos + maxPos) * 0.5);
		} else if (highTime <= minPos) {
			highTime = (int) ((minPos + maxPos) * 0.5);
		} else {
			highTime += (schrittweite * mod);
		}
		pwm.update(highTime);
	}

	public void min() {
		pwm.update(min);
	}

	public void max() {
		pwm.update(max);
	}

}
