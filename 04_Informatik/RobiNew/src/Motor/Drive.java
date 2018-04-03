package Motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import Definitions.RobiConstants;

public abstract class Drive
{
	

	public Drive(boolean enc)
	{

	}

	// -100 <= speed <= 100
	public abstract void setSpeed(int speed);

	public double getPos()
	{
		return 0.0;
	}

	public boolean inPos()
	{
		return true;
	}

	public void setEncoderZero()
	{

	}
}
