package LED;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import java.io.PrintStream;

public class RunningLight extends Task
{

	final static short pinA=10, pinB=11, pinC=12, pinD=13, pinE=14;
	public  static MPIOSM_DIO ledA, ledB, ledC, ledD, swichE;
	
	public RunningLight() {
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
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		RunningLight task = new RunningLight();
		task.period=1000;
		Task.install(task);
		System.out = new PrintStream(sci1.out);
	}
	
	
	public void action()
	{
		//if(swichE.get())
		//{
			 if(ledA.get()) 
			{
				ledA.set(false);
				ledB.set(true);
				ledC.set(false);
				ledD.set(false);
				System.out.println("a");
			}
			else if(ledB.get())
			{
				ledA.set(false);
				ledB.set(false);
				ledC.set(true);
				ledD.set(false);
				System.out.println("b");
			}
			else if(ledC.get())
			{
				ledA.set(false);
				ledB.set(false);
				ledC.set(false);
				ledD.set(true);
				System.out.println("c");
			}
			else if(ledD.get());
			{
				ledA.set(true);
				ledB.set(false);
				ledC.set(false);
				ledD.set(false);
				System.out.println("d");
			}
		}
		//}
		/*
		else if(!swichE.get())
		{
			if(ledA.get()) 
				{
				ledA.set(!ledA.get());
				ledD.set(!ledD.get());
				}
			else if(ledD.get())
			{
				ledD.set(!ledD.get());
				ledC.set(!ledC.get());
			}
			else if(ledC.get())
			{
				ledC.set(!ledC.get());
				ledB.set(!ledB.get());
			}
			else if(ledB.get());
			{
				ledB.set(!ledB.get());
				ledA.set(!ledA.get());
			}
			}
			
	}
	*/
	/*
	public static void ledOff()
	{
		ledA.set(false);
		ledB.set(false);
		ledC.set(false);
		ledD.set(false);
	}
	*/
	
	public static void ausgeben()
	{
		System.out.println(ledA.get());
		System.out.println(ledB.get());
		System.out.println(ledC.get());
		System.out.println(ledD.get());
		
	}
	
}