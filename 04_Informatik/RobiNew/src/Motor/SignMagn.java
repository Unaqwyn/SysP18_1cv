package Motor;

import Definitions.PinMap;
import Definitions.RobiConstants;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class SignMagn extends Drive
{
	
	private TPU_PWM pwmL;
	private TPU_PWM pwmR;
	
	public SignMagn(int PWMChn1, int PWMChn2, int pinEnc, double faktor)
	{
		super(pinEnc, faktor);
		pwmL = new TPU_PWM(PinMap.useTPU_A, (short) PWMChn1, RobiConstants.pwmPeriod, 0);
		pwmR = new TPU_PWM(PinMap.useTPU_A, (short) PWMChn2, RobiConstants.pwmPeriod, 0);
	}
	
	public void setSpeed(int speed)
	{
		if(speed > 0)
		{
			pwmL.update((int) (RobiConstants.pwmPeriod * (speed * 0.01)));
			pwmR.update(0);
			turns = true;
		}
		else if(speed < 0)
		{
			pwmL.update(0);
			pwmR.update((int) ((-1) * (RobiConstants.pwmPeriod * (speed * 0.01))));
			turns = true;
		}
		else
		{
			pwmL.update(0);
			pwmR.update(0);
		}
	}
}
