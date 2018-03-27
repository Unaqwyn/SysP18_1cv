package PWMTest;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class ServoAuto extends Task{
	private final int testChanel = 0;
	private final boolean useTPUA = true;
	
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;
	
	public static int highTime = 0;
	
	public static int counter = 0;
	
	public static int position = setMinPos();
	
	private TPU_PWM pwm;
	
	public ServoAuto(){
		pwm = new TPU_PWM(useTPUA, testChanel, pwmPeriod, highTime);
		period = 100;
		Task.install(this);
	}
	
	public void action(){
		//highTime = (highTime + pwmPeriod / 4) % pwmPeriod;
		if(counter == 0){
			pwm.update(setMinPos());
			counter++;
			System.out.println(counter);
		}
		else if(counter > 0 && counter < 51){
			position += (setMaxPos()-setMinPos())/50;
			pwm.update(position);
			counter++;
			System.out.println(counter);
		}
		else if(counter >= 51 && counter < 100){
			position -= (setMaxPos()-setMinPos())/50;
			pwm.update(position);
			counter++;
			System.out.println(counter);
		}
		else{
			counter = 0;
			System.out.println(counter);
		}
	}
		

	
	static {
		new ServoAuto();
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
	}	
	
	public static int setMinPos(){
		highTime=500000 / TPU_PWM.tpuTimeBase; // 0.2 ms
		return highTime;
	}
	public static int setMaxPos(){
		highTime=2200000 / TPU_PWM.tpuTimeBase; // 2.2 ms
		return highTime;
	}
	public static int setMittelPos(){
		highTime=1200000 / TPU_PWM.tpuTimeBase; // 1.2 ms
		return highTime;
	}
	
}
