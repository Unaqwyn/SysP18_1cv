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

public class TestLift extends Task
{

	public static int height = 0;
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
		INITWIFI,INIT, FORWARD_1, FORWARD_2, BACKWARD_1, BACKWARD_2, SET, LIFT_2, TURN, TURNBACK, DOWN, RESET
	}

	public TestLift() throws Exception
	{
		System.out.println("con starts");
		nSleep = new MDASM_DIO(15, true);
		nSleep.set(true);
		sensors = new Sensoren();
		lift = new Lift(sensors);
		lift.tilt(false);
		System.out.println("con done");
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
		switch (state)
		{
			case INITWIFI:
			{
				System.out.println("initwifi");
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
					System.out.println("switching to forward");
					move.driveForward();
				}
			}
				break;
			case FORWARD_1:
			{
				if (move.inPos())
				{
					state = STATE.FORWARD_2;
					System.out.println("switching to forward 2");
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
					System.out.println("switching to backwards 1");
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
					System.out.println("switching to backwards 2");
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
					System.out.println("switching to Forward 2");
					move.driveForward_2();
				}
			}
				break;
			case BACKWARD_2:
			{
				if (lift.inPosHeight() && move.inPos() && (height==0 || wifi.getReceived()==height+100))
				{
					System.out.println("switching to turn");
					state = STATE.TURN;
					lift.tilt(true);
					move.turnRight();
				}
			}
				break;
			case TURN:
			{
				if (move.platformLeft())
				{
					System.out.println("switching to set");
					state = STATE.SET;
					lift.toHeight(height);
				}
			}
				break;
			case SET:
			{
				//if(lift.inPosHeight())
				if (!first)
				{
					System.out.println("switching to lift 2");
					state = STATE.LIFT_2;
					lift.toHeight(height + 0.5);
				}
				first=false;
			}
				break;
			case LIFT_2:
			{
				if (lift.inPosHeight())
				{
					System.out.println(sensors.readSensor(Sensoren.sensorArm));
					if (!lift.legoFit())
					{
						System.out.println("switch to turnback");
						state = STATE.TURNBACK;
						height += 2;
						wifi.sendCmd(height+99);
						move.turnLeft_1();
					}
					else
					{
						System.out.println("switching to set");
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
					System.out.println("switching to down");
					state = STATE.DOWN;
					move.turnLeft();
					lift.downMin();
				}
			}
				break;
			case DOWN:
			{
					if (height != RobiConstants.target)
					{
						state = STATE.FORWARD_1;
						System.out.println("return to forward 1");
						move.driveForward();
					}
					else
					{
						state = STATE.RESET;
						wifi.pingEnd();
						System.out.println("reset");
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

		TestLift task;
		try
		{
			task = new TestLift();
			task.period = 3000; // Periodenlänge in ms
			Task.install(task); // Installation des Tasks
		} catch (Exception e)
		{
		}
		

	}
}
