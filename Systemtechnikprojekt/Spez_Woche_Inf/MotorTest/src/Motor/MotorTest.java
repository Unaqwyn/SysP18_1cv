package Motor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class MotorTest extends Task{

	private LockedAnti2 la;
	private LEDdemo led;
	
	public MotorTest()
	{
		la=new LockedAnti2();
		led=new LEDdemo();
	}
	
	public void action() 
	{
		la.newSpeed(led.getSpeed());
	}
	
	
	static {
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		// Initialize task
		Task t = new MotorTest();
		t.period = 300;
		Task.install(t);
	}
}
