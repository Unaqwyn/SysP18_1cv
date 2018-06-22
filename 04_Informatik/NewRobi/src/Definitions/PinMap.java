package Definitions;

public class PinMap
{
	// PINs for motors
	public static int pinMain = 0;   //m1
	public static int pinTurnA = 1;
	public static int pinTurnB = 2;
	public static int pinLifting = 3;
	public static int pinInit = 8;		//pin 4 funktioniert nicht, reserve wird verwendet
	public static int pinTilt = 7;
	public static int reserve=8;
	public static int pinVibA=5;
	public static int pinVibB=6;

	
	// Motor Pingruppe
	public static boolean useTPU_A = true;
	
	// PINs for encoders
	public static int pinEncoderLiftingA = 9;
	public static int pinEncoderTurnA = 11;
	public static int pinEncoderInit = 13;
	
	// PINs for sensors, do not ask...
	public static int pinSensOut = 59; 
	public static int pinSensTrig = 5; 
	public static int pinSensA = 6; 
	public static int pinSensB = 7;
	
	// PINs for LEDs
	public static int pinLedPower = 9;
	public static int pinLedWifi = 10;
	public static int pinLedProblem = 11;
	
	// PINs for buttons
	public static int pinStart = 8;
	
	// PINs for motor driver and vibrations motor
	public static int pinSleep = 15; // ??
	public static int pinVibration = 15; // Reserve 5 4??
	
	private PinMap()
	{
	}; // private constructor to block instantiation
}
