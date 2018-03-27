package motor;


import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import definitions.RobotConstants;

/**
 * <h1>Class Motor</h1> implements methods to control the speed and direction of
 * a sign-magnitude driven motor.
 */
public class Motor
{

	private TPU_PWM pwmPlus = null;
	private TPU_PWM pwmMinus = null;
	private int hightimePWMPlus = 0;
	private int hightimePWMMinus = 0;
	
	/**
	 * Constructor initializes the motor.
	 * 
	 * @param useTPUAPlus: set to true if TPUA is used
	 * @param channelAPlus: TPUA-channel of pwm-plus
	 * @param channelAMinus: TPUA-channel of pwm-minus
	 */
	public Motor(boolean useTPUAPlus, int channelAPlus, int channelAMinus)
	{
		pwmPlus = new TPU_PWM(useTPUAPlus, channelAPlus, RobotConstants.PERIOD_PWM, 0);
		pwmMinus = new TPU_PWM(useTPUAPlus, channelAMinus, RobotConstants.PERIOD_PWM, 0);
	}
	
	/**
	 * Method setSpeed() calculates the correct hightime for the given speed.
	 * Speed can vary from +231.5 mm/s to -231.5 mm/s.
	 * However, it is throttled to a quarter of the maximum speed for avoiding too fast speeds.
	 * 
	 * @param speed: speed in mm/s
	 */
	public void  setSpeed(double speed){
		if(speed > 0)
		{
			hightimePWMPlus = (int)((4*speed / RobotConstants.MAX_SPEED) * RobotConstants.PERIOD_PWM);
			if(hightimePWMPlus > (int)(RobotConstants.PERIOD_PWM*0.25))
			{
				hightimePWMPlus = (int)(RobotConstants.PERIOD_PWM*0.25);
			}
			hightimePWMMinus = 0;			
		}else if(speed < 0)
		{
			hightimePWMMinus = (int)((4*speed / RobotConstants.MAX_SPEED) * RobotConstants.PERIOD_PWM);
			hightimePWMMinus *= (-1);
			if(hightimePWMMinus > (int)(RobotConstants.PERIOD_PWM*0.25))
			{
				hightimePWMMinus = (int)(RobotConstants.PERIOD_PWM*0.25);
			}
			hightimePWMPlus = 0;
		}
		else if(speed == 0)
		{
			setStop();
		}
		updatePWM();		
	}
	
	/**
	 * Method setStop() stops the motor.
	 */
	public void setStop(){
		hightimePWMPlus = 0;
		hightimePWMMinus = 0;
		updatePWM();
	}
	
	/**
	 * Method setHalfSpeedReverse() sets the speed to 115 mm/s with the direction
	 * counter-clockwise.
	 */
	public void setHalfSpeedReverse(){
		hightimePWMPlus = (int)(RobotConstants.PERIOD_PWM * 0.5);
		hightimePWMMinus = 0;
		updatePWM();
	}
	
	/**
	 * Method setQuarterSpeedReverse() sets the speed to 60 mm/s with the direction
	 * counter-clockwise.
	 */
	public void setQuarterSpeedReverse(){
		hightimePWMPlus = (int)(RobotConstants.PERIOD_PWM * 0.25);
		hightimePWMMinus = 0;
		updatePWM();
	}
	
	/**
	 * Method setHalfSpeedForward() sets the speed to 115 mm/s with the direction
	 * clockwise.
	 */
	public void setHalfSpeedForward(){
		hightimePWMPlus = 0;
		hightimePWMMinus = (int)(RobotConstants.PERIOD_PWM * 0.5);
		updatePWM();
	}
	
	/**
	 * Method setQuarterSpeedForward() sets the speed to 60 mm/s with the direction
	 * clockwise.
	 */
	public void setQuarterSpeedForward(){
		hightimePWMPlus = 0;
		hightimePWMMinus = (int)(RobotConstants.PERIOD_PWM * 0.25);
		updatePWM();
	}
	
	/**
	 * Method updatePWM() calls the pwmPlus and pwmMinus objects and updates their pwm-signals
	 * with the new hightimes.
	 */
	public void updatePWM(){
		pwmPlus.update(hightimePWMPlus);
		pwmMinus.update(hightimePWMMinus);
	}
}
