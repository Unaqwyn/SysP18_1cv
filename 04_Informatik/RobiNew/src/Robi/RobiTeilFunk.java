package Robi;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import Definitions.IO;
import Motor.LockedAnti;
import Motor.Servo;
import Motor.SignMagn;
import Sensor.Sensoren;

public class RobiTeilFunk extends Task
{
	private LockedAnti la1, la2;
	private SignMagn sm;
	private Servo servo;
	private final int pinLA1=0, pinLA2=3, pinSM1=1, pinSM2=2;	
	private Sensoren sensoren;
	private MDASM_DIO nSleep;
	private boolean on=false;
	private boolean minServo=true;
	private boolean minLA=true;
	
	public RobiTeilFunk()
	{
		la1=new LockedAnti(pinLA1, 0,0);
		la2=new LockedAnti(pinLA2,0,0);
		sm=new SignMagn(pinSM1, pinSM2, 0,0);
		servo=new Servo();
		sensoren=new Sensoren();
		la1.stop();
		la2.stop();
		sm.stop();
		servo.min();
		nSleep=new MDASM_DIO(15, true);
		nSleep.set(false);
	}
	
	public void action()
	{
		if(sensoren.obstacle(0))
		{
			on=!on;
			nSleep.set(on);
		}
		if(on)
		{
			int speed=Sensoren.readSensor(1)/20;
			la1.setSpeed(speed);
			sm.setSpeed(speed);
		}
		if(sensoren.obstacle(2))
		{
			minServo=!minServo;
			if(minServo)
			{
				servo.max();
			}
			else
			{
				servo.min();
			}
		}
		if(sensoren.obstacle(3))
		{
			minLA=!minLA;
			if(minLA)
			{
				la2.toPos(20000);
			}
			else
			{
				la2.toPos(0);
			}
		}
	}

	static {
		Task task=new RobiTeilFunk();
		task.period=100;
		Task.install(task);
	}
}
