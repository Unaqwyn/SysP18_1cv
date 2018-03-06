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

	final static short pinV = 5, pinH = 6, pinC = 7, pinS = 8;
	public static MPIOSM_DIO touchV, touchH, touchC, startTaster;

	private boolean vorwaerts = true;
	private LockedAnti laDrive, laTurn, laArm;
	private LockedAntiEncoder laLift;
	private ServoA servoV, servoKipp;
	private Timer timer, timer2;
	private int h;
	private boolean start;
	private boolean fertig;

	public Robi()
	{
		laDrive = new LockedAnti(0);
		laTurn = new LockedAnti(1);
		laLift = new LockedAntiEncoder(2);
		laArm = new LockedAnti(3);
		timer = new Timer();
		timer2 = new Timer();
		touchV = new MPIOSM_DIO(pinV, false);
		touchH = new MPIOSM_DIO(pinH, false);
		startTaster = new MPIOSM_DIO(pinS, false);
		servoV = new ServoA(4);
		servoKipp = new ServoA(5);
		h = 0;
		start = false;
		fertig = false;

	}

	public void main()
	{
		init(); // Initialisierung

		while(communication() && !start) // Wenn Kommunikation iO und noch nicht Start mach die Schleiffe
		{
			if(startTaster.get()) // Bei Starttaster drücken start Timer und setze start = true
			{
				start = true;
				timer.starten(180000);
			}
		}

		while(start && !timer.abgelaufen() && !fertig) // Wenn start gedrückt, timer noch am laufen und nicht fertig =>
														// bauen
		{
			drive(); // Fahren
			if(vorwaerts) // beim Vorwärtsfahren
			{
				while(!touchV.get()) // Sensor Vorne angeschlagen
				{
				}
				laDrive.stop(); // Stopp vorwärts Fahren
			}
			else if(!vorwaerts) // beim Rückwärtsfahen
			{
				while(!touchH.get()) // Sensor hinten angeschlagen
				{
				}
				laDrive.stop(); // Stopp rückwärts Fahren
			}
			vorwaerts = !vorwaerts; // wechsle von true auf false und umgekehrt
			//
//			
	//		
			if(vorwaerts) // bedingung ändern
			{
				setStone();
			}
			else if(!vorwaerts)
			{
				getStone();
			}
		}
	}

	/**
	 * Initialisierung vor dem Start. - Motoren - Vorbereiten Kommunikation Greifer
	 * bleibt noch hinten und unten (wegen 16x16x16cm)
	 */
	public void init()
	{

	}

	/**
	 * Kommunikation wird aufgebaut Wenn ein Signal gesendet und empfangen wird iO.
	 * Wenn nicht fehler meldung
	 */
	public boolean communication()
	{
		boolean empfangen = false;

		return empfangen;
	}

	/**
	 * Fahren vorwärts oder rückwärts. Geschwindikeit und Zeit einstellen
	 */
	public void drive()
	{
		if(vorwaerts)
		{
			laDrive.setSpeed(50);
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
			laDrive.setSpeed(-50);
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
