package LED;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class LEDdemo extends Task{

	final static short pinA=5, pinB=6, pinC=7;
	public static MPIOSM_DIO ledA, ledB, ledC;
	
	public LEDdemo() {
		ledA=new MPIOSM_DIO(pinA, true);
		ledB=new MPIOSM_DIO(pinB, true);
		ledC=new MPIOSM_DIO(pinC, true);
		ledA.set(true);
		ledB.set(false);
		ledC.set(false);
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new LEDdemo();
		task.period=400;
		Task.install(task);
	}
	
	
	public void action()
	{
		if(ledA.get()==true) 
			{
			ledA.set(!ledA.get());
			ledB.set(!ledB.get());
			}
		else if(ledB.get()==true)
		{
			ledB.set(!ledB.get());
			ledC.set(!ledC.get());
		}
		else if(ledC.get()==true)
		{
			ledC.set(!ledC.get());
			ledA.set(!ledA.get());
		}
	}
	
	
	public static void ledOff()
	{
		ledA.set(false);
		ledB.set(false);
		ledC.set(false);
	}
	
	
}
