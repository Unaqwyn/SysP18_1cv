package LED;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import java.io.PrintStream;

public class SwichLED extends Task{

	final short pinA=5, pinD=9;
	public static MPIOSM_DIO ledA;
	public static MPIOSM_DIO swichD;
	
	public SwichLED() {
		ledA=new MPIOSM_DIO(pinA, true);
		swichD=new MPIOSM_DIO(pinD, false);
		//ledA.set(true);
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new SwichLED();
		task.period=400;
		Task.install(task);
		System.out = new PrintStream(sci1.out);
	}
	
	public void action()
	{
		ledA.set(swichD.get());
	}
	
	public static void ledOff()
	{
		ledA.set(false);
	}
	
	public static void ausgeben()
	{
		System.out.println(swichD.get());
	}
}

