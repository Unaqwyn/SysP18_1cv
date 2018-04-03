package Motor;

import Definitions.PinMap;
import Definitions.RobiConstants;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class LockedAnti extends Drive
{
	private TPU_PWM pwm;
	
	public LockedAnti(boolean enc, int PWMChn)
	{
		super(enc);
		pwm=new TPU_PWM(PinMap.useTPU_A, (short) PWMChn, RobiConstants.pwmPeriod, RobiConstants.pwmPeriod / 2);
	}
	public void setSpeed(int speed)
	{
		int highTime = (int) (RobiConstants.pwmPeriod * (0.5 + (speed * 0.005)));
		pwm.update(highTime);
	}
}
