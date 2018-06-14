package Test;

import java.io.PrintStream;

import Com.Timer;
import Motor.LockedAnti;
import Robi.Lift;
import Robi.Move;
import Sensor.Sensoren;
import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestLift extends Task
{

	Lift lift;
	Move move;
	Timer t;
	private MDASM_DIO nSleep;
	private int z=0;
	private LockedAnti la;
	private Sensoren sensors;
	
	private STATE state;

	private static enum STATE
	{
		INIT,LIFT,SET, LIFT_2, TURN, TURNBACK
	}

	public TestLift()
	{
		System.out.println("con starts");
		
		nSleep = new MDASM_DIO(15, true);
		nSleep.set(true);
		sensors=new Sensoren();
		lift=new Lift(sensors);
		la=new LockedAnti(0,0,0);
		lift.tilt(false);
		state=STATE.INIT;
		System.out.println("con done");
		statemachine();
	}
	
	public void statemachine()
	{
		
		switch (state)
		{
			case INIT:
			{
				System.out.println("initializing");
				lift.init();
			}
			break;
			case LIFT:
			{
				lift.toHeight(4);
			}
			break;
			case SET:
			{
				lift.initDone();
				la=null;
				move=new Move();
				lift.tilt(true);
				lift.toHeight(3);
			}
			break;
			case LIFT_2:
			{
				lift.toHeight(4);
			}
			break;
			case TURN:
			{
				move.turnRight();
			}
			break;
			case TURNBACK:
			{
				move.turnLeft();
			}
			break;
		}
	}

	/*
	public void action()
	{
		if(state.equals(STATE.INIT)&&lift.initComplete())
		{
			state=STATE.LIFT;
			System.out.println("switching to lift");
			statemachine();
		}
		else if(state.equals(STATE.LIFT)&&lift.inPosHeight())
		{
			
			System.out.println("switching to turn");
			state=STATE.TURN;
			statemachine();
		}
		else if(state.equals(STATE.TURN)&&move.platformLeft())
		{
			System.out.println("switching to set");
			state=STATE.SET;
			statemachine();
		}
		else if(state.equals(STATE.LIFT_2)&&lift.inPosHeight())
		{
			//lift.tilt(false);
		}
		else if(state.equals(STATE.SET)&&lift.inPosHeight())
		{
			System.out.println("switching to lift 2");
			state=STATE.TURNBACK;
			statemachine();
		}
		else if(state.equals(STATE.TURNBACK)&&move.platformLeft())
		{
			System.out.println("switching to turnback");
			state=STATE.SET;
			statemachine();
		}
	}
	*/
	
	public void action()
	{
		
		switch (state)
		{
			case INIT:
			{
				if(lift.initComplete())
				{
					state=STATE.LIFT;
					System.out.println("switching to lift");
					statemachine();
				}
			}
			break;
			case LIFT:
			{
				if(lift.inPosHeight())
				{
					System.out.println("switching to turn");
					state=STATE.TURN;
					statemachine();
				}
			}
			break;
			case SET:
			{
				if(lift.inPosHeight())
				{
					System.out.println("switching to turn");
					state=STATE.TURN;
					statemachine();
				}
			}
			break;
			case LIFT_2:
			{
				System.out.println("done");
			}
			break;
			case TURN:
			{
				if(move.platformLeft())
				{
					System.out.println("switching to set");
					state=STATE.SET;
					statemachine();
				}
			}
			break;
			case TURNBACK:
			{
				if(move.platformLeft())
				{
					System.out.println("switching to turnback");
					state=STATE.LIFT_2;
					statemachine();
				}
			}
			break;
		}
	}
	

	static
	{
		// 1) Initialize SCI1 (9600 8n1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// // 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		//TestLift lift = new TestLift();
		 TestLift task = new TestLift();
		 task.period = 3000; // Periodenlänge in ms
		 Task.install(task); // Installation des Tasks

	}
}
