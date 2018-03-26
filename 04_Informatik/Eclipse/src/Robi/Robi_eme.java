package Robi;

import java.io.PrintStream;
import com.Timer;
import com.Wifi;

// import LED.SOS.STATE;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.demo.WifiDemo;
import motor.LockedAnti;
import motor.LockedAntiEncoder;
import motor.Servo;

public class Robi_eme extends Task
{
	private LockedAnti laDrive, laArm;
	private LockedAntiEncoder laLift, laTurn;
	private Servo servoVibration, servoKipp;
	private Timer timer, timer2;

	final static short pinVorne = 5, pinHinten = 6, pinStart = 7;
	public static MPIOSM_DIO sensorVorne, sensorHinten, startTaster;

	public static int hoehe;
	private final int vorgabe;
	private int speed;

	private boolean start;
	private boolean fertig;

	public static enum STATE {
		greiferVorbereiten, vorwaerts, rueckwaerts, drehenLinks, drehenRechts, steinHolen, steinSetzen, greiferHoch, greiferKlappenRunter, greiferKlappenHoch
	}

	private STATE state;

	/**
	 * Konstrukteur
	 */
	public Robi_eme()
	{
		laDrive = new LockedAnti(0);
		laTurn = new LockedAntiEncoder(1, 1);
		laLift = new LockedAntiEncoder(2, 2);
		laArm = new LockedAnti(3);
		servoVibration = new Servo(4);
		servoKipp = new Servo(5);

		sensorVorne = new MPIOSM_DIO(pinVorne, false);
		sensorHinten = new MPIOSM_DIO(pinHinten, false);
		startTaster = new MPIOSM_DIO(pinStart, false);

		timer = new Timer();
		timer2 = new Timer();

		hoehe = 0;
		vorgabe = 9;
		speed = 80;

		start = false;
		fertig = false;

		state = STATE.vorwaerts;
	}

	/**
	 * Main Methode
	 */
	public void main()
	{
		// Initialisieren
		init();

		// Kommunikation
		com();

		// Start gedückt
		start();

		while(start && !fertig)
		{
			switch(state)
			{
				case vorwaerts:
				{
					driveForward();
				}
					break;

				case steinHolen:
				{
					steinHolen();
				}
					break;

				case rueckwaerts:
				{
					driveBack();
				}
					break;

				case drehenRechts:
				{
					drehenRechts();
				}
					break;

				case steinSetzen:
				{
					steinSetzen();
				}
					break;

				case greiferHoch:
				{
					greiferHoch();
				}
					break;

				case drehenLinks:
				{
					drehenLinks();
				}
					break;
			}
		}

	}

	/**
	 * Initialisation Alle PWM bekommen Strom. Encoder werden eingelesen und auf 0
	 * gesetzt
	 */
	private void init()
	{
	}

	/**
	 * Kommunikation aufbauen Kommunikation wird aufgebaut. Durch senden und
	 * empfangen einer Nachricht an den Partenrroboter wird die Kommunikation
	 * geteste.
	 */
	private boolean com()
	{
		return true;
	}

	/**
	 * Startbefehl Wird kontrolliert ob der Starttaster gedrückt wurde
	 */
	private boolean start()
	{
		while(!startTaster.get())
		{
		}
		start = true;
		return start;
	}

	//

	/**
	 * Greifer vorbereiten Greifer anheben und Arm ausfahren
	 */

	/**
	 * Vorwärtsfahren
	 */
	private void driveForward()
	{
		if(hoehe == 0)
		{
			laDrive.setSpeed(speed / 2);
			laArm.toPos();
			if(sensorVorne.get())
			{
				state = STATE.steinHolen;
			}
			else
			{
				laDrive.setSpeed(speed);
				if(sensorVorne.get())
				{
					state = STATE.steinHolen;
				}
			}
		}

	}

	/**
	 * Rückwärtsfahren
	 */
	private void driveBack()
	{
		laDrive.setSpeed(-speed);
		laLift.height(hoehe);

		if(sensorHinten.get())
		{
			state = STATE.drehenRechts;
		}
	}

	/**
	 * Plattform drehen rechts Richtung Turm
	 */
	private void drehenRechts()
	{
		laDrive.stop();
		laTurn.max();
		servoKipp.min();

		if(laTurn.motorInPos())
		{
			state = STATE.steinSetzen;
		}

	}

	//

	/**
	 * Plattform drehen links Richtung Spender
	 */
	private void drehenLinks()
	{
		laTurn.low();
		servoKipp.max();
		if(laTurn.motorInPos())
		{
			Wifi.sendCmd(hoehe);
			if(hoehe != vorgabe)
			{
				state = STATE.vorwaerts;
			}
			else
			{
				fertig = true;
			}
		}
	}

	//

	/**
	 * Stein holen
	 */
	private void steinHolen()
	{
		laDrive.stop();
		state = STATE.rueckwaerts;
	}

	/**
	 * Stein setzen Turm bauen
	 */
	private void steinSetzen()
	{
		laLift.andruecken(hoehe);

		if(laLift.motorInPos())
		{
			hoehe += 2;
			state = STATE.greiferHoch;
		}
	}

	/**
	 * Greifer hoch
	 */
	private void greiferHoch()
	{
		laLift.height(hoehe);
		if(laLift.motorInPos())
		{
			state = STATE.drehenLinks;
		}
	}

}
