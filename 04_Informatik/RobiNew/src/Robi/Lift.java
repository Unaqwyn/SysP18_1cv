package Robi;

import Definitions.PinMap;
import Motor.Encoder;
import Motor.LockedAnti;
import Motor.Servo;
import Motor.SignMagn;
import Sensor.Sensoren;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class Lift extends Task
{
	private Servo tiltMotor;
	private Sensoren sensoren;
	private LockedAnti lifting;
	private LockedAnti moveOutMotor;
	private LockedAnti d; // ?? Für was? Name? ??
	
	public Lift(Sensoren sensoren)
	{
		Task.install(this);
		this.sensoren = sensoren;
		tiltMotor = new Servo();
		lifting = new LockedAnti(PinMap.pinLifting, PinMap.pinEncoderLiftingA, 1); // ?? Faktor? ??
		moveOutMotor = new LockedAnti(PinMap.pinInit, 0, 1); // ?? Faktor? ??
		d = new LockedAnti(3, 3, 3); // ?? Pin Nr. ??
	}
	
	public void init()
	{
		moveOutMotor.setSpeed(50);
		lifting.setEncoderZero();
		
		if(sensoren.obstacle(Sensoren.sensorInit))
		{
			moveOutMotor.stop();
		}
	}
	
	public void downMin()
	{
	}
	
	public void toHeight(int height)
	{
	}
	
	public void tilt(boolean direction) // true = nach unten, false = nach oben
	{
	}
	
	public void setLego()
	{
	}
	
	public void vibrate(boolean on_off)
	{
	}
	
	public boolean inPosHeight()
	{
		return true;
	}
	
	public boolean legoFit()
	{
		return true;
	}
	
	public void action()
	{
	}
	
	static
	{
	}
}
