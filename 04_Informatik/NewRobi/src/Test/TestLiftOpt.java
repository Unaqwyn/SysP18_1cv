package Test;

import java.io.PrintStream;

import Com.Timer;
import Com.Wifi;
import Definitions.IO;
import Definitions.PinMap;
import Definitions.RobiConstants;
import Motor.LockedAnti;
import Robi.Lift;
import Robi.Move;
import Sensor.Sensoren;
import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestLiftOpt extends Task
{

	public int height = 0;
	private Lift lift;
	private Move move;
	private Timer t;
	private Wifi wifi;
	private MDASM_DIO nSleep;
	private int z = 0;
	private LockedAnti la;
	private Sensoren sensors;
	private MPIOSM_DIO startSwitch;
	private boolean first=true;

	private STATE state;

	private static enum STATE
	{
		INITWIFI,INIT, FORWARD_1, FORWARD_2, BACKWARD_1, BACKWARD_2, BACKWARD_3, SET, LIFT_2, TURN, TURNBACK, TURNBACK_2,DOWN, RESET
	}

	public TestLiftOpt() throws Exception
	{
		//System.out.println("con starts");
		nSleep = new MDASM_DIO(15, true);
		nSleep.set(true);
		sensors = new Sensoren();
		lift = new Lift(sensors);
		lift.tilt(false);
		//System.out.println("con done");
		move = new Move(sensors);

		startSwitch = new MPIOSM_DIO(PinMap.pinStart, false);
		while (startSwitch.get())
		{

		}
		wifi=new Wifi();
		lift.downMin();
		state = STATE.INITWIFI;
	}

	public void action()
	{
		wifi.updateReceived();
		wifi.sendCmd(1);
		switch (state)
		{
			case INITWIFI:
			{
				//System.out.println("initwifi");
				if(wifi.getReceived()==223)
				{
					state=STATE.INIT;
					wifi.pingStart();
					lift.init();
				}
				else
				{
					wifi.sendCmd(222);
				}
			}
			break;
			case INIT:
			{
				if (lift.initComplete())
				// if(true)
				{
					state = STATE.FORWARD_1;
					//System.out.println("switching to forward");
					move.driveForward();
				}
			}
				break;
			case FORWARD_1:
			{
				if (move.inPos())
				{
					state = STATE.FORWARD_2;
					//System.out.println("switching to forward 2");
					move.driveForward_2();
					lift.toGround();
				}
			}
				break;
			case FORWARD_2:
			{
				if (move.inPos())
				{
					state = STATE.BACKWARD_1;
					//System.out.println("switching to backwards 1");
					sensors.obstacle();
					move.driveBackward_1();
				}
			}
				break;
			case BACKWARD_1:
			{
				if (lift.legoFit())
				{
					state = STATE.BACKWARD_2;
					//System.out.println("switching to backwards 2");
					move.driveBackward();
					if (height == 0)
					{
						lift.toHeight(1.5);
					}
					else
					{
						lift.toHeight(height + 0.5);
					}
				}
				else if (!lift.legoFit())
				{
					state = STATE.FORWARD_2;
					//System.out.println("switching to Forward 2");
					move.driveForward_2();
				}
				sensors.obstacle();
			}
				break;
			case BACKWARD_2:
			{
				if(!(height==0 || wifi.getReceived()==height+100)) move.stop();
				state=STATE.TURN;
				//System.out.println("switching to turn");
				move.turnRight();
			}
			break;
			case TURN:
			{
				if ((height==0 || wifi.getReceived()==height+100))
				{
					//System.out.println("switching to backwards 3");
					state = STATE.BACKWARD_3;
					move.driveBackward();
					lift.tilt(true);
				}
			}
				break;
			case BACKWARD_3:
			{
				if (lift.inPosHeight() && move.inPos() && move.platformLeft())
				{
					//System.out.println("switching to set");
					state = STATE.SET;
					lift.toHeight(height);
					lift.vibrate(true);
				}
			}
				break;
			case SET:
			{
				//if(lift.inPosHeight())
				if (!first)
				{
					//System.out.println("switching to lift 2");
					state = STATE.LIFT_2;
					lift.toHeight(height + 0.7);
				}
				first=false;
			}
				break;
			case LIFT_2:
			{
				if (lift.inPosHeight())
				{
					if (!lift.legoFit())
					{
						state = STATE.TURNBACK;
						height += 2;
						move.turnLeft_1();
						lift.vibrate(false);
					}
					else
					{
						//System.out.println("switching to set");
						state = STATE.SET;
						lift.toHeight(height);
					}
				}
			}
				break;
			case TURNBACK:
			{
				if (move.platformLeft())
				{
					//System.out.println("switching to turnback_2");
					wifi.sendCmd(height+99);
					state = STATE.TURNBACK_2;
					move.turnLeft();
					lift.downMin();
				}
			}
				break;
			case TURNBACK_2:
			{
					//System.out.println("switching to down");
					state = STATE.DOWN;
			}
				break;
			case DOWN:
			{
					if (height != RobiConstants.target)
					{
						state = STATE.FORWARD_1;
						//System.out.println("return to forward 1");
						move.driveForward();
					}
					else
					{
						state = STATE.RESET;
						wifi.pingEnd();
						//System.out.println("reset");
					}
			}
				break;
			case RESET:
			{
				
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

		TestLiftOpt task;
		try
		{
			task = new TestLiftOpt();
			task.period = 3000; // Periodenlänge in ms
			Task.install(task); // Installation des Tasks
		} catch (Exception e)
		{
		}
		

	}
}
