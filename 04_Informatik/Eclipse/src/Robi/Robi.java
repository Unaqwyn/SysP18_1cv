package Robi;

import java.io.PrintStream;

import com.Timer;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import motor.LockedAnti;
import motor.LockedAntiEncoder;
import motor.ServoA;

public class Robi extends Task
{

	final static short pinV = 5, pinH = 6, pinC = 7;
	public static MPIOSM_DIO touchV, touchH, touchC;

	private boolean vorwaerts = true;
	private LockedAnti laMain, laTurn, laArm;
	private LockedAntiEncoder laLift;
	private ServoA servoV, servoKipp;
	private Timer timer, timer2;
	private int h;



	public Robi()
	{
		timer.starten(180000);
		laMain = new LockedAnti(0);
		laTurn = new LockedAnti(1);
		laLift = new LockedAntiEncoder(2);
		laArm = new LockedAnti(3);
		timer = new Timer();
		timer2 = new Timer();
		touchV = new MPIOSM_DIO(pinV, false);
		touchH = new MPIOSM_DIO(pinH, false);
		h = 0;
		servoV = new ServoA(4);
		servoKipp = new ServoA(5);
	}

	public void main()
	{
		init();
		while(!timer.abgelaufen())
		{
			drive();
			if(vorwaerts)
			{
				while(!touchV.get())
				{
				}
				laMain.stop();
			}
			else if(!vorwaerts)
			{
				while(!touchH.get())
				{
				}
				laMain.stop();
			}
			vorwaerts = !vorwaerts;
			if(vorwaerts)
			{
				setStone();
			}
			else if(!vorwaerts)
			{
				getStone();
			}
		}
	}

	public void drive()
	{
		if(vorwaerts)
		{
			laMain.setSpeed(50);
			timer2.starten(1000);
			while(!(timer2.abgelaufen()))
			{
			}
			laLift.height(h);
			timer2.starten(1000);
			while(!(timer2.abgelaufen()))
			{
			}
			laTurn.min();
		}
		else if(!vorwaerts)
		{
			laMain.setSpeed(-50);
			laTurn.max();
			timer2.starten(1000);
			while(!(timer2.abgelaufen()))
			{
			}
			laLift.low();
		}
	}

	public void getStone()
	{

	}

	public void setStone()
	{
		servoV.vibration();
	}

	public void init()
	{
		laArm.toPos();
	}

	static
	{

		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		System.out = new PrintStream(sci1.out);

		Task task = new Robi();
		task.period = 400;
		Task.install(task);
	}
}
