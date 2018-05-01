package Robi;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.demo.WifiDemo;
import Com.Wifi;
import Definitions.IO;
import Sensor.Sensoren;
import Com.Timer;

public class Robi extends Task
{
	private Move move;
	private Lift lift;
	private Wifi wifi;
	private Timer timer_1;
	private IO io;
	private Sensoren sensoren;
	
	public static int height;
	private int target;
	private boolean start;
	private boolean comIO;
	
	private STATE state;
	
	private static enum STATE
	{
		INITROBI, DRIVEFORWORD_1, GRAP, DRIVEBACKWORD_1, DRIVEBACKWORD_2, WAITSIGNAL, TURNRIGHT, SETLEGO, GRIPPERUP, TURNLEFT, DRIVEFORWORD_2, FINISH
	}
	
	/**
	 * @throws Exception
	 */
	public Robi() throws Exception
	{
		sensoren = new Sensoren();
		move = new Move(sensoren);
		lift = new Lift(sensoren);
		wifi = new Wifi();
		timer_1 = new Timer();
		io = new IO();
		
		height = 0;
		target = 9;
		start = false;
		comIO = false;
		
		state = STATE.INITROBI;
	}
	
	/**
	 * 
	 */
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
				driveForward_1();
			}
				break;
			case GRAP:
			{
				grap();
			}
				break;
			case DRIVEBACKWORD_1:
			{
				driveBackward_1();
			}
				break;
			case DRIVEBACKWORD_2:
			{
				driveBackward_2();
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
				setLego();
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
				driveForward_2();
			}
				break;
			case FINISH:
			{
				finish();
			}
				break;
		}
	}
	
	/**
	 * Initialize the Robi before start. Robi send a testping to our partner. If the
	 * answer is incoming and correct and the start button is pushing, we send the
	 * startsignal to the lighthouse and our partner and change to the next state.
	 */
	public void initRobi()
	{
		start = io.getStartSwitch();
		io.setmotorSleep(true);
		wifi.sendCmd(222);
		
		if(wifi.received == 223)
		{
			comIO = true;
		}
		
		if(start && comIO)
		{
			wifi.sendCmd(800);
			state = STATE.DRIVEFORWORD_1;
			// Sensoren init
		}
	}
	
	/**
	 * Robi drive forward and the lift will be initialize. If the Robi is in front
	 * of the spender, change to the next state.
	 */
	public void driveForward_1()
	{
		lift.init();
		move.driveForwart();
		
		if(!lift.legoFit())
		{
			state = STATE.GRAP;
		}
	}
	
	/**
	 * Robi pick up a Lego. A timer is starting and change to the next state.
	 */
	public void grap()
	{
		timer_1.start(1000);
		
		state = STATE.DRIVEBACKWORD_1;
	}
	
	/**
	 * Robi drive backward, if the timer is lapsed, he is enoug away form the
	 * spender and change to the next state.
	 */
	public void driveBackward_1()
	{
		move.driveBackwart();
		
		if(timer_1.lapsed())
		{
			state = STATE.DRIVEBACKWORD_2;
		}
	}
	
	/**
	 * Robi still drive backward and the lift go up in the right position. If Robi
	 * is in the right back position, change to the next state.
	 */
	public void driveBackward_2()
	{
		lift.toHeight(height +2);
		if(lift.inPosHeight(height+2))
			lift.tilt(true);
		
		if(io.getSensorBack().get())
		{
			state = STATE.WAITSIGNAL;
		}
	}
	
	/**
	 * If the lift is in the right position, the gripper is tilt down. If the
	 * building lot is free, change to the next state.
	 */
	public void waitSignal()
	{
		if(lift.inPosHeight(height+2))
			lift.tilt(true);
		
		if(wifi.next())
		{
			state = STATE.TURNRIGHT;
		}
	}
	
	/**
	 * Robi turn right to the building lot. If the gripper don't tilt he tilt now
	 * down. If the platform is right, change to the next state.
	 */
	public void turnRight()
	{
		move.turnRight();
		
		if(lift.inPosHeight(height+2))
			lift.tilt(true);
		
		if(move.platformRight())
		{
			state = STATE.SETLEGO;
		}
	}
	
	/**
	 * Robi set the Lego. If the Lego if fit, change to the next state.
	 */
	public void setLego()
	{
		lift.setLego(height);
		
		if(lift.legoFit())
		{
			height++;
			lift.vibrate(false);
			state = STATE.GRIPPERUP;
		}
	}
	
	/**
	 * Hier noch mal nachdenken!!
	 */
	public void gripperUp()
	{	
		if(lift.inPosHeight(height+1))
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
	
	public void driveForward_2()
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
