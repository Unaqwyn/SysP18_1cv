package Definitions;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;


public class IO
{
	
	
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
	