package Robi;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.demo.WifiDemo;
import Motor.Lift;
import Motor.Move;
import Com.Wifi;
import Definitions.IO;
import Com.Timer;

public class Robi extends Task
{
	private Move move;
	private Lift lift;
	private Wifi wifi;
	private Timer timer_1;
	private IO io;
	
	public static int height;
	private int target;
	
	private STATE state;
	
	private static enum STATE
	{
		INITROBI, DRIVEFORWORD_1, GRAP, DRIVEBACKWORD_1, DRIVEBACKWORD_2, WAITSIGNAL, TURNRIGHT, SETLEGO, GRIPPERUP, TURNLEFT, DRIVEFORWORD_2, FINISH
	}
	
	public Robi() throws Exception
	{
		move = new Move();
		lift = new Lift();
		wifi = new Wifi();
		timer_1 = new Timer();
		io = new IO();
		
		height = 0;
		target = 9;
		
		state = STATE.INITROBI;
	}
	
	public void statemachine()
	{
		switch(state)
		{
			case INITROBI:
			{
				initRobi();
			}
				break;
			case DRIVEFORWORD_1:
			{
				driveForword_1();
			}
				break;
			case GRAP:
			{
				grap();
			}
				break;
			case DRIVEBACKWORD_1:
			{
				driveBackword_1();
			}
				break;
			case DRIVEBACKWORD_2:
			{
				driveBackword_2();
			}
				break;
			case WAITSIGNAL:
			{
			}
				break;
			case TURNRIGHT:
			{
				turnRight();
			}
				break;
			case SETLEGO:
			{
				SetLego();
			}
				break;
			case GRIPPERUP:
			{
				gripperUp();
			}
				break;
			case TURNLEFT:
			{
				turnLeft();
			}
				break;
			case DRIVEFORWORD_2:
			{
				driveForword_2();
			}
				break;
			case FINISH:
			{
				finish();
			}
				break;
		}
	}
	
	public void initRobi()
	{
		state = STATE.DRIVEFORWORD_1;
	}
	
	public void driveForword_1()
	{
		lift.init();
		move.driveForwart();
		
		if(io.getSensorFront().get())
		{
			state = STATE.GRAP;
		}
	}
	
	public void grap()
	{
		timer_1.start(1000);
		
		state = STATE.DRIVEBACKWORD_1;
	}
	
	public void driveBackword_1()
	{
		move.driveBackwart();
		
		if(timer_1.lapsed())
		{
			state = STATE.DRIVEBACKWORD_2;
		}
	}
	
	public void driveBackword_2()
	{
		lift.toHeight(height);
		if(lift.inPosHeight())
			lift.tilt(true);
		
		if(io.getSensorBack().get())
		{
			state = STATE.WAITSIGNAL;
		}
	}
	
	public void waitSignal()
	{
		if(lift.inPosHeight())
			lift.tilt(true);
		
		if(wifi.next(height))
		{
			state = STATE.TURNRIGHT;
		}
	}
	
	public void turnRight()
	{
		move.turnRight();
		if(lift.inPosHeight())
			lift.tilt(true);
		
		if(move.platformRight())
		{
			state = STATE.SETLEGO;
		}
	}
	
	public void SetLego()
	{
		lift.vibrate(true);
		lift.setLego();
		
		if(lift.legoFit())
		{
			height++;
			lift.vibrate(false);
			state = STATE.GRIPPERUP;
		}
	}
	
	public void gripperUp()
	{
		lift.toHeight(height);
		
		if(lift.inPosHeight())
		{
			state = STATE.TURNLEFT;
		}
		
	}
	
	public void turnLeft()
	{
		move.turnLeft();
		lift.tilt(false);
		lift.downMin();
		wifi.sendHeight();
		
		if(move.platformLeft() && height != target)
		{
			state = STATE.DRIVEFORWORD_2;
		}
		else if(move.platformLeft() && height == target)
		{
			state = STATE.FINISH;
		}
	}
	
	public void driveForword_2()
	{
		move.driveForwart();
		if(io.getSensorFront().get())
		{
			state = STATE.GRAP;
		}
	}
	
	public void finish()
	{
		wifi.sendCmd(801);
	}
	
	public void action()
	{
		statemachine();
	}
	
	static
	{
		// 1) Initialize SCI1 (9600 8n1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		
		// // 2) Use SCI1 for stdout
		// System.out = new PrintStream(sci1.out);
		//
		// // 3) Say hello to the world
		// System.out.println("Hello, world");
		
		try
		{
			Robi task = new Robi();
			task.period = 1000; // Periodenlänge in ms
			Task.install(task); // Installation des Tasks
		}
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
