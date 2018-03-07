package sensor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class DistSensor extends Task
{

	// I/Os to use:
	private final int ADR3PIN = -1; // not used
	private final int ADR2PIN = -1; // not used
	private final int ADR1PIN = 7; // MPIOB7
	private final int ADR0PIN = 6; // MPIOB6
	private final int TRGPIN = 5; // MPIOB5
	private final int ANPIN = 59; // A:AN59
	private HLC1395Pulsed hlc1395;
	int[] werte;
	double durchschnitt = 0;
	public static boolean angestossen = false;

	public void action()
	{
		// System.out.println(hlc1395.read(3));
		for (int i = 0; i < 10; i++)
		{
			if (!contains(0))
			{
				durchschnitt -= werte[i] / 10;
			}
			werte[i] = hlc1395.read(3);
			durchschnitt += werte[i] / 10;
			if (werte[i] >= durchschnitt * 1.2)
			{
				// System.out.print("hinderniss erkannt: ");
				// System.out.print(werte[i]);
				// System.out.print(durchschnitt);
				// System.out.println();
				angestossen = true;
			}
		}

	}

	public boolean hindernis()
	{
		if (angestossen)
		{
			angestossen = false;
			return true;
		}
		return false;
	}

	public boolean contains(int k)
	{
		for (int i = 0; i < 10; i++)
		{
			if (werte[i] == k)
				return true;

		}
		return false;
	}

	public DistSensor()
	{
		hlc1395 = HLC1395Pulsed.getInstance();

		// Initialize sensors
		hlc1395.init(ADR3PIN, ADR2PIN, ADR1PIN, ADR0PIN, TRGPIN, ANPIN);

		// Start reading
		hlc1395.start();
		werte = new int[10];
	}

	static
	{
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		Task t = new DistSensor();
		t.period = 100;
		Task.install(t);

		// Initialize task
	}
}