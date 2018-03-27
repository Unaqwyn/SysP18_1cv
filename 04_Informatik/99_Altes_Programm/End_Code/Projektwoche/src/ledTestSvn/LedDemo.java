package ledTestSvn;

import ch.ntb.inf.deep.runtime.mpc555.driver.MDASM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class LedDemo extends Task{
	final short pinA = 5, pinB = 6, pinC = 7, pinD=9, pinE=8;
	final short pinF = 10, pinG = 11, pinH = 12, pinI = 13, pinJ=14;
	public static MPIOSM_DIO ledA,ledB;
	public static MPIOSM_DIO ledC, ledD;
	public static MPIOSM_DIO switchE, switch1, switch2, switch3;
	public static MPIOSM_DIO ledF, ledG, ledH, ledI;
	public static MPIOSM_DIO switchJ;
	private static State state=State.on;
	public static enum State{on , off}
	private static int i=0;
	private static int Led = 5;
	private MDASM_DIO ledE;
	
	LedDemo(){
		ledB = new MPIOSM_DIO(pinA, true);
		ledA = new MPIOSM_DIO(pinB, true);
		ledC = new MPIOSM_DIO(pinC, true);
		ledD = new MPIOSM_DIO(pinD, true);
		ledE = new MDASM_DIO(12,true);
		
		//switchE = new MPIOSM_DIO(pinE, false);
		switch1 = new MPIOSM_DIO(12, true);
		switch2 = new MPIOSM_DIO(10, true);
		switch3 = new MPIOSM_DIO(8, true);
		switchJ = new MPIOSM_DIO(13, true);
		period = 1000;
		Task.install(this);
	}

	
	public void action(){
		//ledA.set(false);
		/**ledB.set(!switch2.get());
		ledC.set(!switch3.get());
		ledA.set(!switch1.get());
		ledD.set(!switchJ.get());
		ledE.set(!ledE.get());
		*/
		switch1.set(true);
		switch2.set(false);
		switch3.set(false);
	}
	static {
		new LedDemo();
	}
	
}
