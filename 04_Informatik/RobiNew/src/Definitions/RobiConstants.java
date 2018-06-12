package Definitions;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class RobiConstants
{

	public final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;
	public final static int posArm=2750000;
	public final static int posLift=720000;
	public final static int posTurn=-6560000;
}
