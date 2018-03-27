package DCMotor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class LockedAnti {
	private static LockedAnti la;
	
	private final short PWMChn = 4;
	private final boolean useTPUA = true;
	private static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;
	
	private TPU_PWM pwm;
	
	public LockedAnti(){
		pwm = new TPU_PWM(useTPUA, PWMChn, pwmPeriod, pwmPeriod/2);
	}
	public static void hightimeFull(){
		la.update(pwmPeriod);
	}
	public static void hightime3_4(){
		la.update(3 * pwmPeriod / 4);
	}
	public static void hightimeHalf(){
		la.update(pwmPeriod / 2);
	}
	public static void hightime1_4(){
		la.update(pwmPeriod / 4);		
	}
	public static void hightimeZero(){
		la.update(0);
	}
	private void update(int hightime){
		pwm.update(hightime);
		System.out.print(hightime); System.out.print("\t\t"); System.out.print(pwmPeriod); System.out.println();
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		
		la = new LockedAnti();
		
		System.out.println("Hightime \t/\t Period");
	}
}
