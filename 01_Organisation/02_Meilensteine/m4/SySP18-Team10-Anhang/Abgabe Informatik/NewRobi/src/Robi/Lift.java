package Robi;

import Definitions.IO;
import Definitions.PinMap;
import Motor.Drive;
import Motor.Encoder;
import Motor.LockedAnti;
import Motor.Servo;
import Motor.SignMagn;
import Sensor.Sensoren;

import java.io.PrintStream;

import Com.Timer;
import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class Lift
{
	private Servo tiltMotor;
	private Sensoren sensoren;
	private LockedAnti liftingMotor;
	private LockedAnti moveOutMotor;
	private SignMagn vib;
	private int h=0;

	public Lift(Sensoren sensoren)
	{
		this.sensoren = sensoren;
		tiltMotor = new Servo();
		liftingMotor = new LockedAnti(PinMap.pinLifting, PinMap.pinEncoderLiftingA, 1); // ?? Faktor? ??
		moveOutMotor = new LockedAnti(PinMap.pinInit, PinMap.pinEncoderInit, 1); // ?? Faktor? ??
		vib = new SignMagn(PinMap.pinVibA, PinMap.pinVibB, 0, 0);
	}

	public void init()
	{
		moveOutMotor.toPos(Definitions.RobiConstants.posArm, 80); // Richtige Position eingeben!!!!
	}

	public boolean initComplete()
	{
		return moveOutMotor.inPos();
	}
	
	public void initDone()
	{
		moveOutMotor=new LockedAnti(PinMap.pinInit, 0,0);
		liftingMotor=new LockedAnti(PinMap.pinLifting,0,0);
	}

	public void downMin()
	{
		tilt(false);
		liftingMotor.toPos(0,60);
	}

	public void toHeight(int height)
	{
		int h = Definitions.RobiConstants.posLift * height+Definitions.RobiConstants.liftMin;
		liftingMotor.toPos(h,60);
	}

	public void tilt(boolean direction) // true = down, false = up
	{
		if (direction)
		{
			tiltMotor.max();
		}
		else
		{
			tiltMotor.min();
		}
	}

	public void vibrate(boolean on_off) // true = on, false = off
	{
		if (on_off)
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
		return sensoren.threshold(760, Sensoren.sensorArm);
	}

	public Drive getInit()
	{
		return moveOutMotor;
	}

}
