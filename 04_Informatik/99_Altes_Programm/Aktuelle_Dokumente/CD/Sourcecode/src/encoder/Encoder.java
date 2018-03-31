package encoder;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import definitions.RobotConstants;

public class Encoder  extends Task{
	private TPU_FQD encoder = null;
	private long position=0;

	/**
	 * Constructor creates TPU_FQD object.
	 * 
	 * @param inTPUA true if TPUA is used
	 * @param tpuChannel int channel of used TPU
	 */
	public Encoder(boolean inTPUA, int tpuChannel)
	{
		encoder = new TPU_FQD(inTPUA, tpuChannel);
		setPositionZero();
		period = RobotConstants.ENCODER_TASK_PERIOD;
		Task.install(this);
	}
	
	/**
	 * Method action() reads the actual position of encoder repetitively.
	 */
	public void action(){
		position += encoder.getPosition();
		setPositionZero();
	}
	
	/**
	 * Method getPosition() returns the position in ticks.
	 * @return long position of encoder in ticks
	 */
	public long getPosition()
	{
		return position;
	}
	
	/**
	 * Method setZero() resets the encoder and the position.
	 */
	public void setZero()
	{
		position=0;
		encoder.setPosition(0);
	}
	
	/**
	 * Method setPositionZero() resets the encoder.
	 */
	public void setPositionZero()
	{
		encoder.setPosition(0);
	}
}