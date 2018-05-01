package Robi;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import Definitions.PinMap;
import Motor.LockedAnti;
import Motor.SignMagn;
import Sensor.Sensoren;

public class Move extends Task
{
	private LockedAnti main;
	private SignMagn turn;
	private Sensoren sensoren;
	
	public Move(Sensoren sensoren)
	{
		this.sensoren=sensoren;
		main=new LockedAnti(PinMap.pinMain, 0,0);
		turn=new SignMagn(PinMap.pinTurnA, PinMap.pinTurnB, PinMap.pinEncoderTurnA, 1);
		
	}
	
	public void driveForwart()
	{
		main.setSpeed(20);
	}
	
	public void driveBackwart()
	{
		main.setSpeed(-20);
	}
	
	public void turnLeft()
	{
		turn.toPos(0);
	}
	
	//pos testen!!!!!!!!!!!!!!!!!!!!!
	public void turnRight()
	{
		turn.toPos(20000);
	}
	
	public boolean platformLeft()
	{
		return true;
	}
	
	public boolean platformRight()
	{
		return true;
	}
	
	public void action()
	{
		
		
	}
}
