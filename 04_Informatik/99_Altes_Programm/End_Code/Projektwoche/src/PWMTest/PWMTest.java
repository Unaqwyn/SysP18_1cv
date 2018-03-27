package PWMTest;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class PWMTest extends Task{
	
	private final int testChanel = 0;
	private final boolean useTPUA = true;
	
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;
	
	public static int highTime = 0;
	
	private TPU_PWM pwm;
	
	public PWMTest(){
		pwm = new TPU_PWM(useTPUA, testChanel, pwmPeriod, highTime);
		period = 20;
		Task.install(this);
	}
	
	public void action(){
		//highTime = (highTime + pwmPeriod / 4) % pwmPeriod;
		pwm.update(highTime);
	}
	
	static {
		new PWMTest();
	}	
	
	public static void setMinPos(){
		highTime=200000 / TPU_PWM.tpuTimeBase; // 0.2 ms
	}
	public static void setMaxPos(){
		highTime=2200000 / TPU_PWM.tpuTimeBase; // 2.2 ms
	}
	public static void setMittelPos(){
		highTime=1200000 / TPU_PWM.tpuTimeBase; // 1.2 ms
	}
}

