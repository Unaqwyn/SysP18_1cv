package Motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class LEDdemo extends Task{

	final static short pinA=5, pinB=6;
	public static MPIOSM_DIO switchA, switchB;
	
	public LEDdemo() {
		switchA=new MPIOSM_DIO(pinA, false);
		switchB=new MPIOSM_DIO(pinB, false);
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new LEDdemo();
		task.period=400;
		Task.install(task);
	}
	
	public int getSpeed() 
	{
		int val=0;
		if(switchA.get()) 
		{
			val+=2;
		}
		if(switchB.get())
		{
			val++;
		}
		return val;
	}	
	/*
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
	*/
	
}
