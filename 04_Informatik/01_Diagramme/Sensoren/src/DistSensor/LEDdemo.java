package DistSensor;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class LEDdemo extends Task{

	final static short pinA=8, pinB=9, pinC=10, pinD=11, pinE=12, pinF=13, pinG=14, pinH=15;
	public static MPIOSM_DIO switchA, switchB, ledC, ledD, ledE, ledF, ledG, ledH;
	
	public LEDdemo() {
		switchA=new MPIOSM_DIO(pinA, false);
		switchB=new MPIOSM_DIO(pinB, false);
		ledC=new MPIOSM_DIO(pinC, true);
		ledD=new MPIOSM_DIO(pinD, true);
		ledE=new MPIOSM_DIO(pinE, true);
		ledF=new MPIOSM_DIO(pinF, true);
		ledG=new MPIOSM_DIO(pinG, true);
		ledH=new MPIOSM_DIO(pinH, true);
		ledC.set(false);
		ledD.set(false);
		ledE.set(false);
		ledF.set(false);
		ledG.set(false);
		ledH.set(false);
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new LEDdemo();
		task.period=400;
		Task.install(task);
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
	
	public void intToLED(int val)
	{
		if(val>=32)
		{
			ledC.set(true);
			val-=32;
		}
		else ledC.set(false);
		if(val>=16) 
		{
			ledD.set(true);
			val-=16;
		}
		else ledD.set(false);
		if(val>=8) 
		{
			ledE.set(true);
			val-=8;
		}
		else ledE.set(false);
		if(val>=4) 
		{
			ledF.set(true);
			val-=4;
		}
		else ledF.set(false);
		if(val>=2) 
		{
			ledG.set(true);
			val-=2;
		}
		else ledG.set(false);
		if(val>=1) 
		{
			ledH.set(true);
			val-=1;
		}
		else ledH.set(false);
	}
	
	public int whichSensor()
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
	public static void ledOff()
	{
		ledA.set(false);
		ledB.set(false);
		ledC.set(false);
	}
	*/
	
}
