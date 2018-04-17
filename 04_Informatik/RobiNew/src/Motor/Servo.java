package Motor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

import Definitions.PinMap;

public class Servo extends Task {
	private final boolean useTPUA = true;
	// pwmPeriod in TimeBase Unit (50’000 ns)
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;
	public static int highTime = 1200000 / TPU_PWM.tpuTimeBase;
	public static int max = 2200000 / TPU_PWM.tpuTimeBase;
	public static int min = 1200000 / TPU_PWM.tpuTimeBase;

	private static TPU_PWM pwm;
	public boolean auf = true;

	public Servo() {
		pwm = new TPU_PWM(PinMap.useTPU_A, PinMap.pinTilt , pwmPeriod, highTime);
	}

	// 0<=x/y<=1, y!=0
	public void newPos(double x, double y) {
		highTime = (int) (min + (x * (max - min) / y));
		pwm.update(highTime);
	}

	public void min() {
		pwm.update(min);
	}

	public void max() {
		pwm.update(max);
	}

}
