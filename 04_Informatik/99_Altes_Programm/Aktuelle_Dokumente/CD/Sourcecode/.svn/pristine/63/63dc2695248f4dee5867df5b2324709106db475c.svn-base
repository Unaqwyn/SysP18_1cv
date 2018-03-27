package motorController;

import pid.PID;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import definitions.RobotConstants;
import encoder.Encoder;
import motor.Motor;

/**
 * <h1>Class MotorController</h1> controls a motor and its encoder.
 * The motorController can set the desired position to be reached/held and calculates
 * the best speeds to reach/hold that position. The speeds are calculated with a Proportional-
 * Integral-Controller.
 */
public class MotorController extends Task{

	public Encoder encoder = null;
	public Motor motor = null;
	private PID pid = null;
	
	// all speeds are in [mm/s]
	private double speedPID = 0.0;
	
	private double desiredPositionInMM = 0.0;
	private long encoderPosition = 0;
	public double encoderPositionInMM = 0.0;
	public double encoderPositionInMMBefore = 0.0;
	private double lengthInMM = 0.0;
	private final double periodeInSec = ((double)RobotConstants.MOTOR_CONTROLLER_TASK_PERIOD/1000);

	/**
	 * Constructor initializes the motor, encoder, pid and the task.
	 * 
	 * @param useTPUA:	set to true if the TPUA is used
	 * @param channelAPlusMotor:	TPUA-channel for the plus-pol of the motor
	 * @param channelAMinusMotor: TPUA-channel for the minus-pol of the motor
	 * @param channelAEncoder: TPUA-channel of the encoder
	 */
	public MotorController(boolean useTPUA, int channelAPlusMotor, int channelAMinusMotor, int channelAEncoder){
		motor = new Motor(useTPUA, channelAPlusMotor, channelAMinusMotor);
		encoder = new Encoder(useTPUA, channelAEncoder);
		pid = new PID();
		period = RobotConstants.MOTOR_CONTROLLER_TASK_PERIOD;
		Task.install(this);
		encoder.setZero();
	}
	
	/**
	 * Method action() is called repetitively. It calculates the new length of the rope, the new pid speed and sets the motor-speed.
	 */
	public void action()
	{
		getEncoderPosition();
		lengthInMM = desiredPositionInMM - encoderPositionInMM;
		getPIDSpeedInMM();
		motor.setSpeed(speedPID);
	}
	
	/**
	 * Method getEncoderPosition() reads the encoder-position and calculates it from ticks to millimeters.
	 */
	public void getEncoderPosition()
	{
		encoderPositionInMMBefore = encoderPositionInMM;
		encoderPosition = (-1)*encoder.getPosition(); // TODO factor (-1)
		encoderPositionInMM = encoderPosition * RobotConstants.ENCODER_LENGTH_PER_TICK;
	}
	
	/**
	 * Method setDesiredPosition() sets the desired position.
	 * 
	 * @param desiredPos: desired position
	 */
	public void setDesiredPosition(double desiredPos)
	{
		desiredPositionInMM = desiredPos;
	}
	
	/**
	 * Method getPIDSpeedInMM() returns the best-fitted speed for the motor.
	 */
	private void getPIDSpeedInMM()
	{
		speedPID = pid.doPI(desiredPositionInMM, encoderPositionInMM, periodeInSec);
	}
	
	/**
	 * Method reachedDesiredPosition() returns true, when the desired position is reached.
	 * The desired position can vary from +3 mm to -3 mm.
	 * 
	 * @return boolean true when position is reached.
	 */
	public boolean reachedDesiredPosition()
	{
		if(lengthInMM < 3 && lengthInMM > -3)
		{
			return true;
		}
		return false;
	}
}
