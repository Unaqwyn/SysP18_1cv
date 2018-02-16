package Motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class ServoA extends Task {
	private final int testChannel = 1;
	private final boolean useTPUA = true;
	// pwmPeriod in TimeBase Unit (50’000 ns)
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;

	public static int highTime = 1200000 / TPU_PWM.tpuTimeBase;
	public static int newHighTime=2200000 / TPU_PWM.tpuTimeBase;
	public static int max=2200000 / TPU_PWM.tpuTimeBase;
	public static int min=1200000 / TPU_PWM.tpuTimeBase;
	public static int schrittweite=(max-min)/50;

	private static TPU_PWM pwm;
	private static boolean aus=false;
	public boolean auf=true;

	public ServoA(){
 pwm = new TPU_PWM(useTPUA, testChannel, pwmPeriod, highTime);
period = 100; // Periodenlaenge des Tasks in ms
Task.install(this);
 }

	/*
	public void action() {
		highTime = (highTime + pwmPeriod / 4) % pwmPeriod;
		pwm.update(highTime);
	}
	*/
	
	public void action()
	{
		if(!aus)
		{
			if(auf)
			{
				highTime+=schrittweite;
				if(highTime>=max)
				{
					auf=false;
				}
			}
			else if(!auf)
			{
				highTime-=schrittweite;
				if(highTime<=min)
				{
					auf=true;
				}
			}
			pwm.update(highTime);
		}
	}
	

	static { // Task Initialisierung
		new ServoA();
	}
	
	public static void newPos()
	{
		highTime=newHighTime;
		pwm.update(highTime);
		
	}
	
	public static void aus()
	{
		aus=true;
	}
	
	public static void ein()
	{
		aus=false;
	}
}
