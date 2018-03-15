package motor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class LockedAntiEncoder
{
	private final boolean useTPUA = true;
	private Encoder encoder;
	private final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;

	private TPU_PWM pwm;

	public LockedAntiEncoder(int PWMChn, int pinEncoder)
	{

		pwm = new TPU_PWM(useTPUA, (short) PWMChn, pwmPeriod, pwmPeriod / 2);
		this.encoder = new Encoder(pinEncoder);
	}

	public void height(int hight)
	{
		pwm.update(30);
		while (encoder.umrechnen() < hight)
		{
		}
	}

	public void low()
	{
		pwm.update(-30);
		while (encoder.umrechnen() >= 0)
		{
		}
	}

	public void max()
	{

	}

	public void andruecken(int height)
	{

	}
	
	public boolean motorInPos()
	{
		return true;
	}

	static
	{

		// SCI1 initialisieren und als Standardausgabe verwenden
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		// Kopfzeile Ausgeben
		System.out.println("Hightime \t/\t Period");
	}

}
