package Robi;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import Definitions.PinMap;
import Definitions.RobiConstants;
import Motor.LockedAnti;
import Motor.SignMagn;
import Sensor.Sensoren;

public class Move extends Task
{
	private LockedAnti main;
	private SignMagn turn;
	
	private STATE state;
	private Sensoren sensors;
	private MPIOSM_DIO startSwitch;
	private boolean start=false;

	private static enum STATE
	{
		WAITFORSTART, FORWARD_1, FORWARD_2,BACKWARD, NOTMOVING
	}
	
	public Move(Sensoren sensors)
	{
		this.sensors=sensors;
		main=new LockedAnti(PinMap.pinMain, 0,0);
		turn=new SignMagn(PinMap.pinTurnA, PinMap.pinTurnB, PinMap.pinEncoderTurnA, 1);
		state=STATE.WAITFORSTART;
		startSwitch = new MPIOSM_DIO(PinMap.pinStart, false);
		period=200;
		install(this);
	}
	
	public void driveForward()
	{
		main.setSpeed(80);
		state=STATE.FORWARD_1;
		System.out.println("forward");
	}
	
	public void driveForward_2()
	{
		main.setSpeed(20);
		state=STATE.FORWARD_2;
	}
	
	public void driveBackward()
	{
		main.setSpeed(-80);
		state=STATE.BACKWARD;
		System.out.println("backwards");
		}
	
	public void driveBackward_1()
	{
		main.setSpeed(-20);
		state=STATE.BACKWARD;
	}
	
	public void turnLeft_1()
	{
		turn.toPos(RobiConstants.posTurn*0.05,90);
	}
	
	public void turnLeft()
	{
		turn.toPos(0,20);
	}
	
	//pos testen!!!!!!!!!!!!!!!!!!!!!
	public void turnRight()
	{
		turn.toPos(RobiConstants.posTurn,90);
	}
	
	public boolean platformLeft()
	{
		return turn.inPos();
	}
	
	public boolean platformRight()
	{
		return turn.inPos();
	}
	
	public boolean inPos()
	{
		return !main.turning();
	}
	
	public void stop()
	{
		System.out.println("stop");
		main.stop();
		state=STATE.NOTMOVING;
	}
	
	public boolean startPressed()
	{
		return start;
	}
	
	public void action()
	{
		
		switch(state)
		{
			case WAITFORSTART:
			{
				if(!startSwitch.get())
				{
					start=true;
				}
			}
			break;
			case NOTMOVING:
			{
				
			}
			break;
			case FORWARD_1:
			{
				if(sensors.threshold(RobiConstants.sensorSpender,Sensoren.sensorArm))
				{
					System.out.println("bla");
					stop();
				}
			}
			break;
			case FORWARD_2:
			{
				if(sensors.threshold(RobiConstants.sensorLego,Sensoren.sensorArm))
				{
					System.out.println("blabla");
					stop();
				}
			}
			break;
			case BACKWARD:
			{
				if(sensors.obstacle())
				{
					System.out.println("backwards stop");
					stop();
				}
			}
			break;
		}
	}
}
