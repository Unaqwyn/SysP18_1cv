package LED;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import java.util.concurrent.TimeUnit;

public class LEDdemo2 {

	final static short pinA = 5, pinB = 6;
	public static MPIOSM_DIO ledA, ledB;
	public static boolean on=true;

	public LEDdemo2() {
		ledA = new MPIOSM_DIO(pinA, true);
		ledB = new MPIOSM_DIO(pinB, true);
		ledA.set(true);
		ledB.set(false);
	}

	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		action();
	}

	public static void action() {
		while (on) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				ledA.set(!ledA.get());
				ledB.set(!ledB.get());
		}
	}

	public static void ledOff() {
		ledA.set(false);
		ledB.set(false);
		on=false;
	}

}
