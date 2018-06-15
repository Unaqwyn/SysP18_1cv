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
	
	public Move()
	{
		main=new LockedAnti(PinMap.pinMain, 0,0);
		turn=new SignMagn(PinMap.pinTurnA, PinMap.pinTurnB, PinMap.pinEncoderTurnA, 1);
	}
	
	public void driveForwart()
	{
		main.setSpeed(80);
	}
	
	public void driveBackwart()
	{
		main.setSpeed(-80);
	}
	
	public void turnLeft()
	{
		turn.toPos(0);
	}
	
	//pos testen!!!!!!!!!!!!!!!!!!!!!
	public void turnRight()
	{
		turn.toPos(-6560000);
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
		return main.turning();
	}
	
	public void stop()
	{
		main.stop();
	}
}
