package pid;

import definitions.RobotConstants;

/**
 * <h1>Class PID</h1>This class receives the actual position, the desired position and
 * the cycletime to calculate the speed. This ensures a smooth ride to the set position
 * and also the ability to hold the set position.
 */
public class PID {

	private double kProportional = RobotConstants.PID_K_PROPORTIONAL;
	private double kIntegral = RobotConstants.PID_K_INTEGRAL;
	private double sumError = 0.0;
	
	/**
	 * Constructor is empty.
	 */
	public PID(){
		
	}
	
	/**
	 * Method doPI() calculates the value for setting motor-speed.
	 * PI "Proportional"-"Integral" equation is used
	 * to calculate the motor-speed.
	 * 
	 * @param desiredPosition: desired position
	 * @param actualPosition: actual position
	 * @param cycleTime: cycleTime of the motorController-Task
	 * 
	 * @return double speed to set motor
	 */
	public double doPI(double desiredPosition, double actualPosition, double cycleTime){
		
		double output = 0;
		double error = desiredPosition - actualPosition;
		
		if(error < 30 && error > -30)
		{
		sumError = sumError + error;
		}
		else
		{
			sumError=0;
		}
		double outputP = kProportional*error;
		double outputI = kIntegral*(cycleTime)*sumError;
		output = outputP + outputI;
		
		return output;
	}
}
