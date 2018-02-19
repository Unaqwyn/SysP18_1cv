package Servo;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class LEDread extends Task {

	final static short pinA = 5, pinB = 6, pinC = 7, pinD = 8;
	public static MPIOSM_DIO switchA, switchB, switchC, switchD;

	public LEDread() {

		switchA=new MPIOSM_DIO(pinA, false);
		switchB=new MPIOSM_DIO(pinB, false);
		switchC=new MPIOSM_DIO(pinC, false);
		switchD=new MPIOSM_DIO(pinD, false);
	}
	
	public static int readLED()
	{
		int n=0;
		if(switchD.get()) n++;
		if(switchC.get()) n+=2;
		if(switchB.get()) n+=4;
		if(switchA.get()) n+=8;
		return n;
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new LEDread();
		task.period=500;
		Task.install(task);
	}
}
