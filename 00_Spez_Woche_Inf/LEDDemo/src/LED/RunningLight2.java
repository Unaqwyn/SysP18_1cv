package LED;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class RunningLight2 extends Task{

	final static short pinA=5, pinB=6, pinC=7, pinD=8, pinE=9;
	public static MPIOSM_DIO ledA, ledB, ledC, ledD, swichE;
	
	public RunningLight2() {
		ledA=new MPIOSM_DIO(pinA, true);
		ledB=new MPIOSM_DIO(pinB, true);
		ledC=new MPIOSM_DIO(pinC, true);
		ledD=new MPIOSM_DIO(pinD, true);
		swichE=new MPIOSM_DIO(pinE, false);
		ledA.set(true);
		ledB.set(false);
		ledC.set(false);
		ledD.set(false);
	}
	
	public void action()
	{
		if(ledA.get())
		{
			ledA.set(!ledA.get());
			if(swichE.get())
			{
				ledB.set(!ledB.get());
			}
			else
			{
				ledD.set(!ledD.get());
			}
		}
		else if(ledB.get())
		{
			ledB.set(!ledB.get());
			if(swichE.get())
			{
				ledC.set(!ledC.get());
			}
			else
			{
				ledA.set(!ledA.get());
			}
		}
		else if(ledC.get())
		{
			ledC.set(!ledC.get());
			if(swichE.get())
			{
				ledD.set(!ledD.get());
			}
			else
			{
				ledB.set(!ledB.get());
			}
		}
		else if(ledD.get())
		{
			ledD.set(!ledD.get());
			if(swichE.get())
			{
				ledA.set(!ledA.get());
			}
			else
			{
				ledC.set(!ledC.get());
			}
		}
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new RunningLight2();
		task.period=1000;
		Task.install(task);
	}
}
