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
		move = new Move();
		lift = new Lift(sensoren);
		wifi = new Wifi();
		timer_1 = new Timer();
		io = new IO();

		height = 0;

		state = STATE.INITROBI;
	}

	/**
	 * 
	 */
	public void statemachine()
	{
		switch (state)
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
		io.getStartSwitch(); // blockiert bis startknopf gedrückt wird
		wifi.init(); // blockiert bis wifi io, startsignal wird gesendet
		state = STATE.DRIVEFORWORD_1; // state ändern
		statemachine();

	}

	/**
	 * Robi drive forward and the lift will be initialize. If the Robi is in front
	 * of the spender, change to the next state.
	 */
	public void driveForward_1()
	{
		lift.init();
	}

	/**
	 * Robi pick up a Lego. A timer is starting and change to the next state.
	 */
	public void grap()
	{

	}

	/**
	 * Robi drive backward, if the timer is lapsed, he is enoug away form the
	 * spender and change to the next state.
	 */
	public void driveBackward_1()
	{
		move.driveBackwart();
	}

	/**
	 * Robi still drive backward and the lift go up in the right position. If Robi
	 * is in the right back position, change to the next state.
	 */
	public void driveBackward_2()
	{
		lift.toHeight(height + 1);
	}

	/**
	 * If the lift is in the right position, the gripper is tilt down. If the
	 * building lot is free, change to the next state.
	 */
	public void waitSignal()
	{
		lift.tilt(true);
	}

	/**
	 * Robi turn right to the building lot. If the gripper don't tilt he tilt now
	 * down. If the platform is right, change to the next state.
	 */
	public void turnRight()
	{
		move.turnRight();
	}

	/**
	 * Robi set the Lego. If the Lego if fit, change to the next state.
	 */
	public void setLego()
	{
		lift.vibrate(true);
		lift.toHeight(height);
	}

	/**
	 * Hier noch mal nachdenken!!
	 */
	public void gripperUp()
	{
		lift.toHeight(height+1);
		lift.vibrate(false);
	}

	public void turnLeft()
	{
		height+=2;
		move.turnLeft();
		lift.tilt(false);
		wifi.sendHeight();
	}

	public void driveForward_2()
	{
		move.driveForwart();
		lift.downMin();
	}

	public void finish()
	{
		wifi.sendCmd(801);
	}

	public void action()
	{
		if(state.equals(STATE.DRIVEFORWORD_1)&&lift.initComplete())
		{
			state=STATE.DRIVEFORWORD_2;
			statemachine();
		}
		if (state.equals(STATE.DRIVEFORWORD_2) && lift.legoFit())
		{
			move.stop();
			state = STATE.GRAP;
			statemachine();
		}
		else if (state.equals(STATE.GRAP))
		{
			state = STATE.DRIVEBACKWORD_1;
		}
		else if (state.equals(STATE.DRIVEBACKWORD_1))
		{
			state = STATE.DRIVEBACKWORD_2;
			statemachine();
		}
		else if (state.equals(STATE.DRIVEBACKWORD_2) && sensoren.obstacle())
		{
			move.stop();
			state = STATE.WAITSIGNAL;
			statemachine();
		}
		else if (state.equals(STATE.WAITSIGNAL) && (height == 0 || wifi.next())&&lift.inPosHeight())
		{
			state = STATE.TURNRIGHT;
			statemachine();
		}
		else if (state.equals(STATE.TURNRIGHT) && move.platformLeft())
		{
			state = STATE.SETLEGO;
			statemachine();
		}
		else if (state.equals(STATE.SETLEGO) && lift.inPosHeight())
		{
			state = STATE.GRIPPERUP;
			statemachine();
		}
		else if (state.equals(STATE.GRIPPERUP) && lift.inPosHeight()&&lift.legoFit())
		{
			state = STATE.TURNLEFT;
			statemachine();
		}
		else if (state.equals(STATE.GRIPPERUP) && lift.inPosHeight()&&!lift.legoFit())
		{
			state = STATE.SETLEGO;
			statemachine();
		}
		else if (state.equals(STATE.TURNLEFT) && move.platformLeft())
		{
			state = STATE.DRIVEFORWORD_2;
			statemachine();
		}
		else if (state.equals(STATE.TURNLEFT) && height==6)
		{
			state = STATE.FINISH;
			statemachine();
		}
	}

	static
	{
		// 1) Initialize SCI1 (9600 8n1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// // 2) Use SCI1 for stdout
		// System.out = new PrintStream(sci1.out);

		try
		{
			Robi task = new Robi();
			task.period = 1000; // PeriodenlÃ¤nge in ms
			Task.install(task); // Installation des Tasks
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
