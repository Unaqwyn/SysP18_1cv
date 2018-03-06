package test;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestTouch extends Task{
	final static short pinA=5, pinB=6;
	public static MPIOSM_DIO ledA, touchB;
	
	public TestTouch()
	{
		ledA=new MPIOSM_DIO(pinA,true);
		ledA.set(false);
		touchB=new MPIOSM_DIO(pinB, false);
	}
	
	public void action()
	{
		ledA.set(!touchB.get());
		System.out.println(touchB.get());
	}

	static {

		// SCI1 initialisieren und als Standardausgabe verwenden
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		Task task = new TestTouch();
		task.period=400;
		Task.install(task);
	}
}
