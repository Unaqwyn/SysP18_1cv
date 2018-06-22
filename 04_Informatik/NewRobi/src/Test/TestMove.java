package Test;

import java.io.PrintStream;

import Motor.LockedAnti;
import Robi.Lift;
import Robi.Move;
import Sensor.Sensoren;
import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestMove extends Task
{
	private Move move;
	private boolean forward=false;
	private LockedAnti la;
	private MDASM_DIO nSleep;
	
	public TestMove()
	{
		
		nSleep = new MDASM_DIO(15, true);
		nSleep.set(true);
		move=new Move(new Sensoren());
		Lift lift=new Lift(null);
		lift.tilt(false);
	}
	
	
	public void action()
	{
		if(forward)
		{
			move.driveForward();
			//la.setSpeed(50);
		}
		else
		{
			
			move.driveBackward();
			//la.setSpeed(-50);
		}
		forward=!forward;
	}
	
	
	static
	{
		MPIOSM_DIO wifiIO;
		wifiIO = new MPIOSM_DIO(10, true);
		wifiIO.set(false);

		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// // 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);
		
		 Task task = new TestMove();
		 task.period = 10000; // Periodenlänge in ms
		 Task.install(task); // Installation des Tasks
		 
		System.out.println("TestMove started");

	}
}
