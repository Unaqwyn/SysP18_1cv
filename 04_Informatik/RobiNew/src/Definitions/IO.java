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

	/**
	 * @return the laMain
	 */
	public MPIOSM_DIO getLaMain()
	{
		return laMain;
	}

	/**
	 * @param laMain the laMain to set
	 */
	public void setLaMain(MPIOSM_DIO laMain)
	{
		this.laMain = laMain;
	}

	/**
	 * @return the smTurn_A
	 */
	public MPIOSM_DIO getSmTurn_A()
	{
		return smTurn_A;
	}

	/**
	 * @param smTurn_A the smTurn_A to set
	 */
	public void setSmTurn_A(MPIOSM_DIO smTurn_A)
	{
		this.smTurn_A = smTurn_A;
	}

	/**
	 * @return the smTurn_B
	 */
	public MPIOSM_DIO getSmTurn_B()
	{
		return smTurn_B;
	}

	/**
	 * @param smTurn_B the smTurn_B to set
	 */
	public void setSmTurn_B(MPIOSM_DIO smTurn_B)
	{
		this.smTurn_B = smTurn_B;
	}

	/**
	 * @return the laLifting
	 */
	public MPIOSM_DIO getLaLifting()
	{
		return laLifting;
	}

	/**
	 * @param laLifting the laLifting to set
	 */
	public void setLaLifting(MPIOSM_DIO laLifting)
	{
		this.laLifting = laLifting;
	}

	/**
	 * @return the seTilt
	 */
	public MPIOSM_DIO getSeTilt()
	{
		return seTilt;
	}

	/**
	 * @param seTilt the seTilt to set
	 */
	public void setSeTilt(MPIOSM_DIO seTilt)
	{
		this.seTilt = seTilt;
	}

	/**
	 * @return the smVibration_A
	 */
	public MPIOSM_DIO getSmVibration_A()
	{
		return smVibration_A;
	}

	/**
	 * @param smVibration_A the smVibration_A to set
	 */
	public void setSmVibration_A(MPIOSM_DIO smVibration_A)
	{
		this.smVibration_A = smVibration_A;
	}

	/**
	 * @return the smVibration_B
	 */
	public MPIOSM_DIO getSmVibration_B()
	{
		return smVibration_B;
	}

	/**
	 * @param smVibration_B the smVibration_B to set
	 */
	public void setSmVibration_B(MPIOSM_DIO smVibration_B)
	{
		this.smVibration_B = smVibration_B;
	}

	/**
	 * @return the motorMainSleep
	 */
	public MPIOSM_DIO getMotorMainSleep()
	{
		return motorMainSleep;
	}

	/**
	 * @param motorMainSleep the motorMainSleep to set
	 */
	public void setMotorMainSleep(MPIOSM_DIO motorMainSleep)
	{
		this.motorMainSleep = motorMainSleep;
	}

	/**
	 * @return the motorTurnSleep
	 */
	public MPIOSM_DIO getMotorTurnSleep()
	{
		return motorTurnSleep;
	}

	/**
	 * @param motorTurnSleep the motorTurnSleep to set
	 */
	public void setMotorTurnSleep(MPIOSM_DIO motorTurnSleep)
	{
		this.motorTurnSleep = motorTurnSleep;
	}

	/**
	 * @return the motorLiftingSleep
	 */
	public MPIOSM_DIO getMotorLiftingSleep()
	{
		return motorLiftingSleep;
	}

	/**
	 * @param motorLiftingSleep the motorLiftingSleep to set
	 */
	public void setMotorLiftingSleep(MPIOSM_DIO motorLiftingSleep)
	{
		this.motorLiftingSleep = motorLiftingSleep;
	}

	/**
	 * @return the motorTiltSleep
	 */
	public MPIOSM_DIO getMotorTiltSleep()
	{
		return motorTiltSleep;
	}

	/**
	 * @param motorTiltSleep the motorTiltSleep to set
	 */
	public void setMotorTiltSleep(MPIOSM_DIO motorTiltSleep)
	{
		this.motorTiltSleep = motorTiltSleep;
	}

	/**
	 * @return the motorVibrationSleep
	 */
	public MPIOSM_DIO getMotorVibrationSleep()
	{
		return motorVibrationSleep;
	}

	/**
	 * @param motorVibrationSleep the motorVibrationSleep to set
	 */
	public void setMotorVibrationSleep(MPIOSM_DIO motorVibrationSleep)
	{
		this.motorVibrationSleep = motorVibrationSleep;
	}

	/**
	 * @return the encoderLifting_A
	 */
	public MPIOSM_DIO getEncoderLifting_A()
	{
		return encoderLifting_A;
	}

	/**
	 * @param encoderLifting_A the encoderLifting_A to set
	 */
	public void setEncoderLifting_A(MPIOSM_DIO encoderLifting_A)
	{
		this.encoderLifting_A = encoderLifting_A;
	}

	/**
	 * @return the encoderLifting_B
	 */
	public MPIOSM_DIO getEncoderLifting_B()
	{
		return encoderLifting_B;
	}

	/**
	 * @param encoderLifting_B the encoderLifting_B to set
	 */
	public void setEncoderLifting_B(MPIOSM_DIO encoderLifting_B)
	{
		this.encoderLifting_B = encoderLifting_B;
	}

	/**
	 * @return the encoderTurn_A
	 */
	public MPIOSM_DIO getEncoderTurn_A()
	{
		return encoderTurn_A;
	}

	/**
	 * @param encoderTurn_A the encoderTurn_A to set
	 */
	public void setEncoderTurn_A(MPIOSM_DIO encoderTurn_A)
	{
		this.encoderTurn_A = encoderTurn_A;
	}

	/**
	 * @return the encoderTurn_B
	 */
	public MPIOSM_DIO getEncoderTurn_B()
	{
		return encoderTurn_B;
	}

	/**
	 * @param encoderTurn_B the encoderTurn_B to set
	 */
	public void setEncoderTurn_B(MPIOSM_DIO encoderTurn_B)
	{
		this.encoderTurn_B = encoderTurn_B;
	}

	/**
	 * @return the sensorFront
	 */
	public MPIOSM_DIO getSensorFront()
	{
		return sensorFront;
	}

	/**
	 * @param sensorFront the sensorFront to set
	 */
	public void setSensorFront(MPIOSM_DIO sensorFront)
	{
		this.sensorFront = sensorFront;
	}

	/**
	 * @return the sensorBack
	 */
	public MPIOSM_DIO getSensorBack()
	{
		return sensorBack;
	}

	/**
	 * @param sensorBack the sensorBack to set
	 */
	public void setSensorBack(MPIOSM_DIO sensorBack)
	{
		this.sensorBack = sensorBack;
	}

	/**
	 * @return the sensorArm
	 */
	public MPIOSM_DIO getSensorArm()
	{
		return sensorArm;
	}

	/**
	 * @param sensorArm the sensorArm to set
	 */
	public void setSensorArm(MPIOSM_DIO sensorArm)
	{
		this.sensorArm = sensorArm;
	}

	/**
	 * @return the ledPower
	 */
	public MPIOSM_DIO getLedPower()
	{
		return ledPower;
	}

	/**
	 * @param ledPower the ledPower to set
	 */
	public void setLedPower(MPIOSM_DIO ledPower)
	{
		this.ledPower = ledPower;
	}

	/**
	 * @return the ledWifi
	 */
	public MPIOSM_DIO getLedWifi()
	{
		return ledWifi;
	}

	/**
	 * @param ledWifi the ledWifi to set
	 */
	public void setLedWifi(MPIOSM_DIO ledWifi)
	{
		this.ledWifi = ledWifi;
	}

	/**
	 * @return the ledProblem
	 */
	public MPIOSM_DIO getLedProblem()
	{
		return ledProblem;
	}

	/**
	 * @param ledProblem the ledProblem to set
	 */
	public void setLedProblem(MPIOSM_DIO ledProblem)
	{
		this.ledProblem = ledProblem;
	}

	/**
	 * @return the startSwitch
	 */
	public MPIOSM_DIO getStartSwitch()
	{
		return startSwitch;
	}

	/**
	 * @param startSwitch the startSwitch to set
	 */
	public void setStartSwitch(MPIOSM_DIO startSwitch)
	{
		this.startSwitch = startSwitch;
	}

	/**
	 * @return the sleepSwitch
	 */
	public MPIOSM_DIO getSleepSwitch()
	{
		return sleepSwitch;
	}

	/**
	 * @param sleepSwitch the sleepSwitch to set
	 */
	public void setSleepSwitch(MPIOSM_DIO sleepSwitch)
	{
		this.sleepSwitch = sleepSwitch;
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
	