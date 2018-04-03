package Motor;

import Definitions.PinMap;
import Definitions.RobiConstants;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class SignMagn extends Drive
{

	private TPU_PWM pwmL;
	private TPU_PWM pwmR;
	
	public SignMagn(boolean enc, int PWMChn1, int PWMChn2)
	{
		super(enc);
		pwmL=new TPU_PWM(PinMap.useTPU_A, (short) PWMChn1, RobiConstants.pwmPeriod, RobiConstants.pwmPeriod / 2);
		pwmR=new TPU_PWM(PinMap.useTPU_A, (short) PWMChn2, RobiConstants.pwmPeriod, RobiConstants.pwmPeriod / 2);
	}
	
	public void setSpeed(int speed)
	{
		if(speed>0)
		{
			pwmL.update((int) (RobiConstants.pwmPeriod * (speed * 0.01)));
			pwmR.update(0);
		}
		else if(speed<0)
		{
			pwmR.update((int) ((-1)*(RobiConstants.pwmPeriod * (speed * 0.01))));
			pwmL.update(0);
		}
		else
		{
			pwmL.update(0);
			pwmR.update(0);
		}
	}
}
