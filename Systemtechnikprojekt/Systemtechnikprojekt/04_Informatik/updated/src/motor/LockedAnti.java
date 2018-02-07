package motor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class LockedAnti {
	private static LockedAnti la; // Diese Klasse hat eine Instanz von sich selber

	private final short PWMChn = 0;
	private final boolean useTPUA = true;
	private final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;

	private TPU_PWM pwm;

	public LockedAnti() {
		// PWM-Kanal initialisieren
		pwm = new TPU_PWM(useTPUA, PWMChn, pwmPeriod, pwmPeriod / 2);
	}

	/*
	 * private void update(int hightime) { pwm.update(hightime);
	 * System.out.print(hightime);System.out.print("\t/\t");
	 * System.out.print(pwmPeriod); System.out.println(); }
	 */

	// Geschwindigkeit setzten
	// -100<=speed<=100
	public void setSpeed(int speed) {
		int highTime = (int) (pwmPeriod * (0.5 + (speed * 0.005)));
		pwm.update(highTime);
	}

	public void stop() {
		setSpeed(0);
	}
	
	public void min()
	{
		
	}
	
	public void max()
	{
		
	}
	
	public void height(int hight)
	{
		
	}
	
	public void low()
	{
		
	}
	
	public void toPos()
	{
		
	}

	static {

		// SCI1 initialisieren und als Standardausgabe verwenden
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		// Objekt erzeugen
		la = new LockedAnti();

		// Kopfzeile Ausgeben
		System.out.println("Hightime \t/\t Period");
	}

}
