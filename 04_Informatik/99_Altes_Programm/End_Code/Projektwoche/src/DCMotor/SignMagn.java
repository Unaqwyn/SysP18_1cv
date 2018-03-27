package DCMotor;

import java.io.PrintStream;


import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class SignMagn {

	private static SignMagn sm;
	
	private final boolean useTPUA = true;
	private final int chnL = 0, chnR = 2;
	private final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;
	
	private int currHightimeL, currHightimeR;
	private TPU_PWM pwmL, pwmR;
	
	public SignMagn(){
		currHightimeL=0;
		currHightimeR=0;
		pwmL = new TPU_PWM(useTPUA, chnL, pwmPeriod,  currHightimeL);
		pwmR = new TPU_PWM(useTPUA, chnR, pwmPeriod, currHightimeR);
	}
	public static void leftFull(){
		sm.update(pwmPeriod, 0);
	}
	public void leftHalf(){
		sm.update(pwmPeriod / 2, 0);
	}
	public static void rightFull(){
		sm.update(0, pwmPeriod);
	}
	public static void rightHalf(){
		sm.update(0, pwmPeriod / 2);
	}
	public static void stop(){
		sm.update(0, 0);
	}
	
	private void update(int hightimeL, int hightimeR){
		currHightimeL = hightimeL;
		currHightimeR = hightimeR;
		pwmL.update(hightimeL);
		pwmR.update(hightimeR);
		
		System.out.print(hightimeL); System.out.print("\t\t");
		System.out.print(hightimeR); System.out.print("\t\t");
		System.out.print(pwmPeriod); System.out.println();
	}
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		
		sm = new SignMagn();
		
		System.out.println("Hightime Left \t\t Hightime Right \t\t Period");
	}
}
