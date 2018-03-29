package Definitions;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class IO
{
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
	
	// Output Motordriver
	private MPIOSM_DIO motorSleep;
	
	public IO()
	{
		// sensorFront = new MPIOSM_DIO(PinMap.pin , false);
		// sensorBack = new MPIOSM_DIO(PinMap.pin , false);
		// sensorArm = new MPIOSM_DIO(PinMap.pin , false);
		
		ledPower = new MPIOSM_DIO(PinMap.pinLedPower, true);
		ledWifi = new MPIOSM_DIO(PinMap.pinLedWifi, true);
		ledProblem = new MPIOSM_DIO(PinMap.pinLedProblem, true);
		
		startSwitch = new MPIOSM_DIO(PinMap.pinStart, false);
		
		motorSleep = new MPIOSM_DIO(PinMap.pinSleep, true);
	}
	
	/**
	 * @return the sensorFront
	 */
	public MPIOSM_DIO getSensorFront()
	{
		return sensorFront;
	}
	
	/**
	 * @param sensorFront
	 *            the sensorFront to set
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
	 * @param sensorBack
	 *            the sensorBack to set
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
	 * @param sensorArm
	 *            the sensorArm to set
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
	 * @param ledPower
	 *            the ledPower to set
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
	 * @param ledWifi
	 *            the ledWifi to set
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
	 * @param ledProblem
	 *            the ledProblem to set
	 */
	public void setLedProblem(MPIOSM_DIO ledProblem)
	{
		this.ledProblem = ledProblem;
	}
	
	/**
	 * @return the startSwitch
	 */
	public boolean getStartSwitch()
	{
		return startSwitch.get();
	}
	
	/**
	 * @return the sleepSwitch
	 */
	public MPIOSM_DIO getMotorSleep()
	{
		return motorSleep;
	}
	
	/**
	 * @param sleepSwitch
	 *            the sleepSwitch to set
	 */
	public void setmotorSleep(boolean change)
	{
		this.motorSleep.set(change);
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
