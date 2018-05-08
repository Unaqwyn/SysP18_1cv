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
	private Timer timer;
	private IO ios;
	
	private double faktor;
	private double andrücken;
	
	public Lift(Sensoren sensoren)
	{
		ios = new IO();
		this.sensoren = sensoren;
		tiltMotor = new Servo();
		liftingMotor = new LockedAnti(PinMap.pinLifting, PinMap.pinEncoderLiftingA, 1); // ?? Faktor? ??
		moveOutMotor = new LockedAnti(PinMap.pinInit, 0, 1); // ?? Faktor? ??
		
		timer = new Timer();
		
		faktor = 1;
		andrücken = 0.5;
		
		Task.install(this);
	}
	
	public void init()
	{
		moveOutMotor.setSpeed(50);
		
		if(sensoren.obstacle(Sensoren.sensorInit))
		{
			moveOutMotor.stop();
		}
	}
	
	public void downMin()
	{
		liftingMotor.setSpeed(-50);
		if(liftingMotor.getPos() == 0)
		{
			liftingMotor.stop();
		}
	}
	
	public void toHeight(int height)
	{
		liftingMotor.toPos(height * faktor);
	}
	
	public void tilt(boolean direction) // true = down, false = up
	{
		if(direction)
		{
			tiltMotor.min();
		}
		else
		{
			tiltMotor.max();
		}
	}
	
	public void setLego(int height)
	{
		vibrate(true);
		liftingMotor.toPos((height + 1) * faktor - andrücken);
		timer.start(500);
		if(timer.lapsed())
		{
			liftingMotor.toPos(height + 2);
			vibrate(false);
		}
	}
	
	public void vibrate(boolean on_off) // true = on, false = off
	{
		ios.setVibrationsMotor(on_off);
	}
	

	public boolean inPosHeight()
	{
		return liftingMotor.inPos();
	}
	
	//
	//
	// fragen threshold wann gibt er true zurück
	//
	// true = näher grenzwert
	//
	public boolean legoFit()
	{
		if(sensoren.threshold(200, Sensoren.sensorArm))
		{
			return true;
		}
		return false;
	}
	
	public void action()
	{
	}

}
