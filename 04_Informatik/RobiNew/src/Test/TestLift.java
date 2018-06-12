package Test;

import java.io.PrintStream;

import Com.Timer;
import Robi.Lift;
import Sensor.Sensoren;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestLift extends Task
{
	
	Lift lift;
	Timer t = new Timer();
	
	public TestLift()
	{
		Lift lift = new Lift(new Sensoren());
		bla();
		lift.tilt(false);
	}
	
	public void bla()
	{
		lift.init();
		t.start(30000);
		while(!t.lapsed())
		{
		}
		lift.toHeight(5);
		while(!lift.inPosHeight())
		{
		}
			lift.tilt(true);
//			lift.vibrate(true);
			lift.setLego(4);
			
	
	
	static
	{
		// 1) Initialize SCI1 (9600 8n1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		
		// // 2) Use SCI1 for stdout
		 System.out = new PrintStream(sci1.out);
		
		 TestLift lift=new TestLift();
		//TestLift task = new TestLift();
		//task.period = 1000; // Periodenlänge in ms
		//Task.install(task); // Installation des Tasks
		
	}
}
