package ios;


import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import definitions.PinMap;

/**
* <h1>I/O's</h1>
* Sets the status of the leds and the start button
* <p>
*
* @author  Andreas Jung
* @version 1.0
* @since   2017-03-07
*/

public class IO {
	
	private  MPIOSM_DIO startSwitch;
	private  MPIOSM_DIO rollMotorLeftButton;
	private  MPIOSM_DIO rollMotorRightButton;
	private  MPIOSM_DIO rollMotorMiddleButton;
	private  MPIOSM_DIO ledSearch;
	private  MPIOSM_DIO ledDock;
	private  MPIOSM_DIO ledLand;
	private  MDASM_DIO ledCommunication;
	private  MPIOSM_DIO magnet;
	private  MPIOSM_DIO ledTilt;
	private MPIOSM_DIO optOnOff;
	private MDASM_DIO motorLRDisableSleep;
	private MDASM_DIO motorFDisableSleep;
	
	
	/**
	 * The constructor of the class I_O_s
	 * Initialize the start button and the leds
	 */
	public IO () 
	{
		startSwitch = new MPIOSM_DIO(PinMap.SWITCH1_MPIOB_PIN, false);
		ledSearch=new MPIOSM_DIO(PinMap.LED1_MPIOB_PIN, true);
		ledDock = new MPIOSM_DIO(PinMap.LED2_MPIOB_PIN, true);
		ledLand = new MPIOSM_DIO(PinMap.LED4_MPIOB_PIN, true);
		ledCommunication = new MDASM_DIO(PinMap.LED5_MDA_PIN, true);
		ledTilt = new MPIOSM_DIO(PinMap.LED3_MPIOB_PIN, true);
		magnet = new MPIOSM_DIO(PinMap.MAGNET_SWITCH_MPIOB_PIN, true);
		rollMotorLeftButton = new MPIOSM_DIO(PinMap.BUTTON1_MOTOR_LEFT_MPIOB_PIN, false);
		rollMotorMiddleButton = new MPIOSM_DIO(PinMap.BUTTON2_MOTOR_MIDDLE_MPIOB_PIN, false);
		rollMotorRightButton = new MPIOSM_DIO(PinMap.BUTTON3_MOTOR_RIGHT_MPIOB_PIN, false);
		optOnOff = new MPIOSM_DIO(PinMap.OPT_SWITCH_MPIOB_PIN, true);
		motorLRDisableSleep = new MDASM_DIO(PinMap.LEFT_RIGHT_MOTORS_DISABLE_SLEEP, true);
		motorFDisableSleep = new MDASM_DIO(PinMap.FRONT_MOTOR_DISABLE_SLEEP, true);
	}
	
	/**
	 * Checks whether the start button is pressed or not
	 * @return false if the start button is pressed
	 */
	public boolean getStartSwitch() {
			return startSwitch.get();
	}
	
	/**
	 * Returns the variable ledSearch
	 * @return true for switched on
	 */
	public boolean getLedSearch() {
		return this.ledSearch.get();
	}

	/**
	 * Returns the variable ledDock
	 * @return true for switched on
	 */
	public boolean getLedDock() {
		return this.ledDock.get();
	}
	
	/**
	 * Returns the variable ledLand
	 * @return true for switched on
	 */
	public boolean getLedLand() {
		return this.ledLand.get();
	}
	
	/**
	 * Returns the variable ledCommunication
	 * @return true for switched on
	 */
	public boolean getLedCommunication() {
		return this.ledCommunication.get();
	}
	
	/**
	 * Returns the variable ledTilt
	 * @return true for switched on 
	 */
	public boolean getLedTilt() {
		return this.ledTilt.get();
	}
	
	/**
	 * Returns the variable magnet 
	 * @return true for switched on 
	 */
	public boolean getMagnet() {
		return this.magnet.get();
	}
	
	/**
	 * Checks whether the button for the left motor is pressed or not
	 * @return false if button is pressed
	 */
	public boolean getButtonMotorLeft(){
		return this.rollMotorLeftButton.get();
	}
	
	/**
	 * Checks whether the button for the middle motor is pressed or not
	 * @return false if button is pressed
	 */
	public boolean getButtonMotorMiddle(){
		return this.rollMotorMiddleButton.get();
	}
	
	/**
	 * Checks whether the button for the right motor is pressed or not
	 * @return false if button is pressed
	 */
	public boolean getButtonMotorRight(){
		return this.rollMotorRightButton.get();
	}
	
	/**
	 * Returns the variable optOnOff
	 * @return true for switched on 
	 */
	public boolean getOptOnOff(){
		return this.optOnOff.get();
	}
	
	/**
	 * Set the variable ledSearch 
	 * @param ledSearch: true for switched on 
	 */
	public void setLedSearch(boolean ledSearch) {
		this.ledSearch.set(ledSearch);
	}

	/**
	 * Set the variable ledDock
	 * @param ledDock: true for switched on
	 */
	public void setLedDock(boolean ledDock) {
		this.ledDock.set(ledDock);
	}
	
	/**
	 * Set the variable ledLand
	 * @param ledLand: true for switched on
	 */
	public void setLedLand(boolean ledLand) {
		this.ledLand.set(ledLand);
	}
	
	/**
	 * Set the variable ledCommunication
	 * @param ledCommunication: true for switched on
	 */
	public void setLedCommunication(boolean ledCommunication) {
		this.ledCommunication.set(ledCommunication);
	}
	
	/**
	 * ledTilt the ledTilt to set
	 * @param ledTilt: true for switched on 
	 */
	public void setLedTilt(boolean ledTilt) {
		this.ledTilt.set(ledTilt);
	}
	
	/**
	 * Magnet the magnet to set
	 * @param magnetOn: true for switched on 
	 */
	public void setMagnet(boolean magnetOn) {
		this.magnet.set(magnetOn);
	}
	
	/**
	 * OptOnOff the opt to set
	 * @param optOnOff: true for switched on 
	 */
	public void setOptOnOff(boolean optOnOff) {
		this.optOnOff.set(optOnOff);
	}
	
	/**
	 * Disable sleep of external motor-driver-board of Motor
	 * Left and Right.
	 * @param disableSleep: true for switched on 
	 */
	public void setLRDisableSleep(boolean disableSleep) {
		this.motorLRDisableSleep.set(disableSleep);
	}
	
	/**
	 * Disable sleep of external motor-driver-board of Motor
	 * Front.
	 * @param disableSleep: true for switched on 
	 */
	public void setFDisableSleep(boolean disableSleep) {
		this.motorFDisableSleep.set(disableSleep);
	}
	
	/**
	 * Turns all LED's off
	 */
	public void allLedOff(){
		ledCommunication.set(false);
		ledDock.set(false);
		ledLand.set(false);
		ledTilt.set(false);
		ledSearch.set(false);
	}
	
	/**
	 * Turns all LED's on
	 */
	public void allLedOn(){
		ledCommunication.set(true);
		ledDock.set(true);
		ledLand.set(true);
		ledTilt.set(true);
		ledSearch.set(true);
	}

}
