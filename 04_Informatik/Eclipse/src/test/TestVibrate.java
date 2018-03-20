package test;


import motor.Servo;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import java.io.PrintStream;
public class TestVibrate extends Task{
	
	private Servo servoA;
	private final short pinA=5;
	private MPIOSM_DIO touchA;
	
	public TestVibrate()
	{
		touchA=new MPIOSM_DIO(pinA,false);
		servoA=new Servo(0);
	}
	
	public void action()
	{
		while(touchA.get())
		{
			servoA.vibration();
		}
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		System.out = new PrintStream(sci1.out);

		Task task = new TestVibrate();
		task.period = 10;
		Task.install(task);
	}
}
