package Robi;

import Definitions.IO;
import Definitions.PinMap;
import Motor.Encoder;
import Motor.LockedAnti;
import Motor.Servo;
import Motor.SignMagn;
import Sensor.Sensoren;
import Com.Timer;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class Lift extends Task
{
	private Servo tiltMotor;
	private Sensoren sensoren;
	private LockedAnti liftingMotor;
	private LockedAnti moveOutMotor;
	private SignMagn vib;
	private Timer timer;
	private IO ios;
	
	public Lift(Sensoren sensoren)
	{
		ios = new IO();
		this.sensoren = sensoren;
		tiltMotor = new Servo();
		liftingMotor = new LockedAnti(PinMap.pinLifting, PinMap.pinEncoderLiftingA, 1); // ?? Faktor? ??
		moveOutMotor = new LockedAnti(PinMap.pinInit, PinMap.pinEncoderInit, 1); // ?? Faktor? ??
		vib=new SignMagn(PinMap.pinVibA,PinMap.pinVibB,0,0);
		
		
		timer = new Timer();
		
		// Task.install(this);
	}
	
	public void init()
	{
		moveOutMotor.toPos(Definitions.RobiConstants.posArm); // Richtige Position eingeben!!!!
	}
	
	public void downMin()
	{
		liftingMotor.toPos(0);
	}
	
	public void toHeight(int height)
	{
		int h=Definitions.RobiConstants.posLift*height;
		liftingMotor.toPos(h);
	}
	
	public void tilt(boolean direction) // true = down, false = up
	{
		if(direction)
		{
			tiltMotor.max();
		}
		else
		{
			tiltMotor.min();
		}
	}
	
	public void setLego(int height)
	{
		vibrate(true);
		
		toHeight(height + 1); // runter
		timer.start(500);
		while(!timer.lapsed())
		{
			//
		}
		toHeight(height + 2);
		vibrate(false);
	}
	
	public void vibrate(boolean on_off) // true = on, false = off
	{
		if(on_off)
		{
			vib.setSpeed(100);
		}
		else
		{
			vib.stop();
		}
	}
	
	public boolean inPosHeight()
	{
		return liftingMotor.inPos();
	}
	
	// true = näher grenzwert
	//
	public boolean legoFit()
	{
		return sensoren.threshold(600, Sensoren.sensorArm);
	}
	
	// public void action()
	// {
	// }
	
}
