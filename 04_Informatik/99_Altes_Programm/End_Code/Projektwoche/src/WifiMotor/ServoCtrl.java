package WifiMotor;

import ch.ntb.inf.deep.runtime.mpc555.*;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class ServoCtrl {
	final static boolean useTPUA = true;
	final static int pwmChannel = 4;
	final static int pwmPeriode = 20000000 / TPU_PWM.tpuTimeBase;
	final static int minHighTime = 1200000 / TPU_PWM.tpuTimeBase;
	final static int maxHighTime = 2200000 / TPU_PWM.tpuTimeBase;
	final static int offsetPerDegree = (maxHighTime-minHighTime)/90;
				
	private static TPU_PWM pwm;
	
	public ServoCtrl() {
		pwm = new TPU_PWM(useTPUA, pwmChannel, pwmPeriode, minHighTime);
	}

	public static void setPosition(int degree) {
		if(degree >= 0 && degree <= 90){
			pwm.update(minHighTime+(degree*offsetPerDegree));
		}		
	}
	static {
		pwm = new TPU_PWM(useTPUA, pwmChannel, pwmPeriode, minHighTime);
	}
}

