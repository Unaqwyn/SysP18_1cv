package test;


import motor.LockedAnti;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import java.io.PrintStream;
public class TestMotor extends Task{
	
	private final short pinA=5, pinB=6;
	private MPIOSM_DIO touchA, touchB;
	private LockedAnti la;
	boolean vorwaerts;
	
	public TestMotor()
	{
		touchA=new MPIOSM_DIO(pinA,false);
		touchB=new MPIOSM_DIO(pinB, false);
		la= new LockedAnti(0);
	}
	
	public void action()
	{
		if(!touchA.get()&&!touchB.get())
		{
			if(vorwaerts)
			{
				la.setSpeed(50);
			}
			else if(!vorwaerts)
			{
				la.setSpeed(-50);
			}
		}
		else if(touchA.get()&&!touchB.get())
		{
			vorwaerts=true;
			la.setSpeed(0);
		}
		else if(!touchA.get()&&touchB.get())
		{
			vorwaerts=false;
			la.setSpeed(0);
		}
		else
		{
			la.setSpeed(0);
		}
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		System.out = new PrintStream(sci1.out);

		Task task = new TestMotor();
		task.period = 100;
		Task.install(task);
	}
}
