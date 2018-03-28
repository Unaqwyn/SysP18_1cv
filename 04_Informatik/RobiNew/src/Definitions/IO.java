package Definitions;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;

public class IO
{
	// Inputs motors
	private MPIOSM_DIO laMain;
	private MPIOSM_DIO smTurn_A;
	private MPIOSM_DIO smTurn_B;
	private MPIOSM_DIO laLifting;
	private MPIOSM_DIO seTilt;
	private MPIOSM_DIO smVibration_A;
	private MPIOSM_DIO smVibration_B;
	
	// Outputs motors drivers
	private MPIOSM_DIO motorMainSleep;
	private MPIOSM_DIO motorTurnSleep;
	private MPIOSM_DIO motorLiftingSleep;
	private MPIOSM_DIO motorTiltSleep;
	private MPIOSM_DIO motorVibrationSleep;
	
	// Inputs encoders
	private MPIOSM_DIO encoderLifting_A;
	private MPIOSM_DIO encoderLifting_B;
	private MPIOSM_DIO encoderTurn_A;
	private MPIOSM_DIO encoderTurn_B;
	
	// Inputs sensors
	private MPIOSM_DIO sensorFront;
	private MPIOSM_DIO sensorBack;
	private MPIOSM_DIO sensorArm;
	
	// Outputs LEDs
	private MPIOSM_DIO ledPower;
	private MPIOSM_DIO ledWifi;
	private MPIOSM_DIO ledProblem;
	
	// Inputs switches
	private MPIOSM_DIO startSwitch;
	private MPIOSM_DIO sleepSwitch;
	
	public IO()
	{
		laMain = new MPIOSM_DIO(PinMap.pinMain, true);
		smTurn_A = new MPIOSM_DIO(PinMap.pinTurnA, true);
		smTurn_B = new MPIOSM_DIO(PinMap.pinTurnB, true);
		laLifting = new MPIOSM_DIO(PinMap.pinLifting, true);
		seTilt = new MPIOSM_DIO(PinMap.pinTilt, true);
		smVibration_A = new MPIOSM_DIO(PinMap.pinVibrationA, true);
		smVibration_B = new MPIOSM_DIO(PinMap.pinVibrationB, true);
		
//		motorMainSleep = new MPIOSM_DIO(PinMap.pin , true);
//		motorTurnSleep = new MPIOSM_DIO(PinMap.pin , true);
//		motorLiftingSleep = new MPIOSM_DIO(PinMap.pin , true);
//		motorTiltSleep = new MPIOSM_DIO(PinMap.pin , true);
//		motorVibrationSleep = new MPIOSM_DIO(PinMap.pin , true);	
		
		encoderLifting_A = new MPIOSM_DIO(PinMap.pinEncoderLiftingA, false);
		encoderLifting_B = new MPIOSM_DIO(PinMap.pinEncoderLiftingB, false);
		encoderTurn_A = new MPIOSM_DIO(PinMap.pinEncoderTurnA, false);
		encoderTurn_B = new MPIOSM_DIO(PinMap.pinEncoderTurnB, false);
		
		// sensorFront = new MPIOSM_DIO(PinMap.pin , false);
		// sensorBack = new MPIOSM_DIO(PinMap.pin , false);
		// sensorArm = new MPIOSM_DIO(PinMap.pin , false);
		
		ledPower = new MPIOSM_DIO(PinMap.pinLedPower, true);
		ledWifi = new MPIOSM_DIO(PinMap.pinLedWifi, true);
		ledProblem = new MPIOSM_DIO(PinMap.pinLedProblem, true);
		
		startSwitch = new MPIOSM_DIO(PinMap.pinStart, false);
		sleepSwitch = new MPIOSM_DIO(PinMap.pinSleep, false);
	}
	
	public boolean getLaMain()
	{
		return laMain.get();
	}
	
	public boolean getSmTurn_A()
	{
		return smTurn_A.get();
	}
	
	public boolean getSmTurn_B()
	{
		return smTurn_B.get();
	}
	
	public boolean getLaLifting()
	{
		return laLifting.get();
	}
	
	public boolean getSeTilt()
	{
		return seTilt.get();
	}
	
	public boolean getSmVibration_A()
	{
		return smVibration_A.get();
	}
	
	public boolean getVibration_B()
	{
		return smVibration_B.get();
	}
	
	//	
	
	public boolean getMotorMainSleep()
	{
		return motorMainSleep.get();
	}
	
	public boolean getMotorTurnSleep()
	{
		return motorTurnSleep.get();
	}
	
	public boolean getMotorLiftingSleep()
	{
		return motorLiftingSleep.get();
	}
	
	public boolean getMotorTiltSleep()
	{
		return motorTiltSleep.get();
	}
	
	public boolean getMotorVibrationSleep()
	{
		return motorVibrationSleep.get();
	}
	
	//
	
	public boolean getEncoderLifting_A()
	{
		return encoderLifting_A.get();
	}
	
	public boolean getEncoderLifting_B()
	{
		return encoderLifting_B.get();
	}
	
	public boolean getEncoderTurn_A()
	{
		return encoderTurn_A.get();
	}
	
	public boolean getEncoderTurn_B()
	{
		return encoderTurn_B.get();
	}
	
	//
	
	public boolean getSensorFront()
	{
		return sensorFront.get();
	}
	
	public boolean getSensorBack()
	{
		return sensorBack.get();
	}
	
	public boolean getSensorArm()
	{
		return sensorArm.get();
	}
	
	//
	
	public boolean getLedPower()
	{
		return ledPower.get();
	}
	
	public boolean getLedWifi()
	{
		return ledWifi.get();
	}
	
	public boolean getLedProblem()
	{
		return ledProblem.get();
	}
	
	//
	
	public boolean getStartSwitch()
	{
		return startSwitch.get();
	}
	
	public boolean getSleepSwitch()
	{
		return sleepSwitch.get();
	}
	
	//
	
	public void setMotorMainSleep(boolean change) 
	{
		motorMainSleep.set(change);
	}
	
	public void setMotorTurnSleep(boolean change) 
	{
		motorTurnSleep.set(change);
	}
	
	public void setMotorLiftingSleep(boolean change) 
	{
		motorLiftingSleep.set(change);
	}
	
	public void setMotorTiltSleep(boolean change) 
	{
		motorTiltSleep.set(change);
	}
	
	public void setMotorVibrationSleep(boolean change) 
	{
		motorVibrationSleep.set(change);
	}
	
	public void setAllMotorSleepOFF()
	{
		motorMainSleep.set(false);;
		motorTurnSleep.set(false);;
		motorLiftingSleep.set(false);;
		motorTiltSleep.set(false);;
		motorVibrationSleep.set(false);;
	}
	
	public void setAllMotorSleepON()
	{
		motorMainSleep.set(true);;
		motorTurnSleep.set(true);;
		motorLiftingSleep.set(true);;
		motorTiltSleep.set(true);;
		motorVibrationSleep.set(true);;
	}
	//
	
	public void setLedPower(boolean change) 
	{
		ledPower.set(change);
	}
	
	public void setLedWifi(boolean change) 
	{
		ledPower.set(change);
	}
	
	public void setLedProblem(boolean change) 
	{
		ledProblem.set(change);
	}
	
	public void setAllLedOff()
	{
		ledPower.set(false);
		ledPower.set(false);
		ledProblem.set(false);
	}
	
	public void setAllLedOn()
	{
		ledPower.set(true);
		ledPower.set(true);
		ledProblem.set(true);
	}
}
