package Motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class MotorA extends Task {
	private final int testChannel = 0;
	private final boolean useTPUA = true;
	private static boolean aus=false;
	// pwmPeriod in TimeBase Unit (50�000 ns)
	private final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;

	public static int highTime = 0 / TPU_PWM.tpuTimeBase;

	private static TPU_PWM pwm;

	public MotorA(){
 pwm = new TPU_PWM(useTPUA, testChannel, pwmPeriod, highTime);
period = 5000; // Periodenlaenge des Tasks in ms
Task.install(this);
 }

	public void action() {
		if(aus==false)
		{
		highTime = (highTime + pwmPeriod / 4) % pwmPeriod;
		pwm.update(highTime);
		}
	}

	public static void ein()
	{
		aus=false;
		highTime = (highTime + pwmPeriod / 4) % pwmPeriod;
		pwm.update(highTime);
		
	}

	public static void aus()
	{
		highTime=0;
		pwm.update(highTime);
		aus=true;
	}
	static { // Task Initialisierung
		new MotorA();
	}
}
