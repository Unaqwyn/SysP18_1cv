package DistSensor;


import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import ch.ntb.inf.deep.runtime.ppc32.Task;
//import DistSensor.*;


public class DistToLED extends Task{
	
	private DistSensorTest dist;
	private LEDdemo led;
	
	public DistToLED()
	{
		dist=new DistSensorTest();
		led=new LEDdemo();
	}
	
	public void action()
	{
		led.intToLED(dist.distToInt(led.whichSensor()));
	}

	
	static {
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		// Initialize task
		Task t = new DistToLED();
		t.period = 500;
		Task.install(t);
	}
}
