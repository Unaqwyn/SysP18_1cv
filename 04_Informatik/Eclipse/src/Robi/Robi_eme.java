package Robi;

import java.io.PrintStream;
import com.Timer;
//import LED.SOS.STATE;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import motor.LockedAnti;
import motor.LockedAntiEncoder;
import motor.ServoA;

public class Robi_eme extends Task
{
	private LockedAnti laDrive, laArm;
	private LockedAntiEncoder laLift, laTurn;
	private ServoA servoV, servoKipp;
	private Timer timer, timer2;

	final static short pinVorne = 5, pinHinten = 6, pinStart = 7;
	public static MPIOSM_DIO touchVorne, touchHinten, startTaster;

	private int hoehe;
	private int vorgabe;
	
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
		laTurn = new LockedAntiEncoder(1);
		laLift = new LockedAntiEncoder(2);
		laArm = new LockedAnti(3);
		servoV = new ServoA(4);
		servoKipp = new ServoA(5);

		touchVorne = new MPIOSM_DIO(pinVorne, false);
		touchHinten = new MPIOSM_DIO(pinHinten, false);
		startTaster = new MPIOSM_DIO(pinStart, false);

		timer = new Timer();
		timer2 = new Timer();

		hoehe = 0;
		vorgabe = 5;
		
		start = false;
		fertig = false;

		state = STATE.greiferVorbereiten;
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

		while(start &&!fertig)
		{
			switch(state)
			{
				case greiferVorbereiten:
				{
					greiferVorbereiten();
				}
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
				case greiferHoch:
				{
					greiferHoch();
				}
					break;
					
				case greiferKlappenRunter:
				{
					greiferKlappenRunter();
				}
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
					
				// greiferHoch
					
				case greiferKlappenHoch:
				{
					greiferKlappenHoch();
				}
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
		
		return start;
	}

	/**
	 * Greifer vorbereiten Greifer anheben und Arm ausfahren
	 */
	private void greiferVorbereiten()
	{

	}

	/**
	 * Vorwärtsfahren
	 */
	private void driveForward()
	{
		
	}

	/**
	 * Rückwärtsfahren
	 */
	private void driveBack()
	{
	}

	/**
	 * Plattform drehen rechts Richtung Turm
	 */
	private void drehenRechts()
	{
	}

	//

	/**
	 * Plattform drehen links Richtung Spender
	 */
	private void drehenLinks()
	{
		// if() methode no laTurn rückmeldung
		{
			state = STATE.vorwaerts;
		}
		
		hoehe++;
		if(hoehe == vorgabe) fertig = true;
	}

	//

	/**
	 * Stein holen
	 */
	private void steinHolen()
	{
	}

	/**
	 * Greifer hoch
	 */
	private void greiferHoch()
	{
	}

	/**
	 * Stein setzen Turm bauen
	 */
	private void steinSetzen()
	{
	}

	//

	/**
	 * Greifer nach unten klappen
	 */
	private void greiferKlappenRunter()
	{
	}

	/**
	 * Greifer nach oben klappen
	 */
	private void greiferKlappenHoch()
	{
	}
}
