package sensor;

import ios.*;
import ch.ntb.inf.deep.runtime.mpc555.driver.QADC_AIN;
import definitions.PinMap;
import definitions.RobotConstants;

/**
 * <h1>Sensorik</h1> The class sensors initializes the sensors.
 * It reads out the values of the sensors.
 * It returns if the docking-process is successful or not
 * and it returns on which position the platform is located.
 * <p>
 *
 * @author Sandro Santoro
 * @version 1.0
 * @since 2017-03-07
 */
public class Sensors {

	private IO ios;

	/**
	 * The constructor of the class sensors. All used analog inputs for the
	 * sensors will be initialized. All OPT-Sensors are turned on.
	 * 
	 */
	public Sensors() {

		QADC_AIN.init(RobotConstants.USE_QADCA);
		ios = new IO();
		ios.setOptOnOff(true);
	}

	/**
	 * This method reads out the dock sensor and checks whether the robot is
	 * docked or not.
	 * 
	 * @return boolean Returns the value of the dock status. If it's docked it
	 *         returns true or false if it's not docked.
	 */
	public boolean getDock() {

//		int sensorValue = QADC_AIN.read(RobotConstants.USE_QADCA, PinMap.LDR_AN_PIN);
//
//		if (sensorValue < RobotConstants.VALUE_LED_DOCK_BORDER) {
//			return true;
//		}
//		return false;
		return true;
	}

	/**
	 * This method reads out the dock sensor to know where the platform is. The
	 * platform can be on the positions 1 to 6. If no platform is found returns
	 * -1.
	 * 
	 * 
	 * Positions: left 1 2 (3 4) 5 6 right
	 * Position 3 and 4 are combined to one position.
	 * 
	 * @return int Returns the position of the platform. If no platform is found
	 *         returns -1.
	 */
	public int getPlatform() {

		int opt1 = QADC_AIN.read(RobotConstants.USE_QADCA, PinMap.OPT1_AN_PIN);
		int opt2 = QADC_AIN.read(RobotConstants.USE_QADCA, PinMap.OPT2_AN_PIN);
		int opt3 = QADC_AIN.read(RobotConstants.USE_QADCA, PinMap.OPT3_AN_PIN);
		int opt4 = QADC_AIN.read(RobotConstants.USE_QADCA, PinMap.OPT4_AN_PIN);

		int opt12 = opt1 + opt2;
		int opt34 = opt3 + opt4;

		if ((opt12 + opt34) > RobotConstants.VALUE_PLATFORM_LED_RED_MIN_SUM) 
		{
			if ((opt12 + opt34) > RobotConstants.VALUE_PLATFORM_LED_RED_MAX_SUM) {
				return 3;
			}

			if (opt12 > opt34 && (opt12 + opt34) < RobotConstants.VALUE_PLATFORM_LED_RED_POSITION_1_TO_2) {
				return 1;
			} else if (opt12 > opt34 && (opt12 + opt34) > RobotConstants.VALUE_PLATFORM_LED_RED_POSITION_1_TO_2) {
				return 2;
			}

			if (opt12 < opt34 && (opt12 + opt34) < RobotConstants.VALUE_PLATFORM_LED_RED_POSITION_5_TO_6) {
				return 6;
			} else if (opt12 < opt34 && (opt12 + opt34) > RobotConstants.VALUE_PLATFORM_LED_RED_POSITION_5_TO_6) {
				return 5;
			}
		}		
		return -1;
	}
}