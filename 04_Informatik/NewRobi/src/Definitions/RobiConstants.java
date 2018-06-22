package Definitions;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class RobiConstants
{
	public static final int target=8;
	
	public final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;
	//public final static int posArm=2700000;
	public final static int posArm=2860000;
	public final static int posLift=428000;
	public final static int posTurn=-7330000;
	public static final int liftMin=620000;
	public static final int ground=-10000;
	public static final int min=100000;
	
	public static final int sensorSpender=600;
	public static final int sensorLego=730;
}
