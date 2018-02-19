package bin;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class LEDdemo extends Task{

	final static short pinA=5;
	public static MPIOSM_DIO ledA;
	
	public LEDdemo() {
		ledA=new MPIOSM_DIO(pinA, false);
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new LEDdemo();
		task.period=400;
		Task.install(task);
	}
	
	public boolean getValue()
	{
		return ledA.get();
	}
}
