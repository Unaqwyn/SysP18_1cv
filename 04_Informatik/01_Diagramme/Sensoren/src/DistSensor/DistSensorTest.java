package DistSensor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class DistSensorTest extends Task
{

	// I/Os to use:
	MPIOSM_DIO ledA;
	private final int ADR3PIN = -1; // not used
	private final int ADR2PIN = -1; // not used
	private final int ADR1PIN = 7; // MPIOB7
	private final int ADR0PIN = 6; // MPIOB6
	private final int TRGPIN = 5; // MPIOB5
	private final int ANPIN = 59; // A:AN59
	private HLC1395Pulsed hlc1395;
	int[] werte;
	double durchschnitt=0;

	

	public void action()
	{
		while(true)
		{
		boolean angestossen=false;
		for(int i=0;i<10;i++)
		{
			durchschnitt-=werte[i]/10;
			werte[i]=hlc1395.read(3);
			durchschnitt+=werte[i]/10;
			if(hlc1395.read(3)>=durchschnitt*3/2)
		}
		}
	}
	

	public void distToPrint()
	{
		int min = hlc1395.read(0);
		int max = hlc1395.read(0);
		int maxSensor = 0;
		int minSensor = 0;
		for (int i = 1; i < 4; i++)
		{
			if (hlc1395.read(i) > max)
			{
				max = hlc1395.read(i);
				maxSensor = i;
			}
			else if (hlc1395.read(i) < min)
			{
				min = hlc1395.read(i);
				minSensor = i;
			}
		}
		System.out.print("Min:");
		System.out.print("\t");
		System.out.print(minSensor);
		System.out.print("\t");
		System.out.print(min);
		System.out.print("\t");
		System.out.print("Max:");
		System.out.print("\t");
		System.out.print(maxSensor);
		System.out.print("\t");
		System.out.print(max);
		System.out.print("\t");
		System.out.println();
	}

	public int distToInt(int n)
	{
		int val = hlc1395.read(n);
		val /= 32;
		return val;
	}

	public DistSensorTest()
	{
		hlc1395 = HLC1395Pulsed.getInstance();

		// Initialize sensors
		hlc1395.init(ADR3PIN, ADR2PIN, ADR1PIN, ADR0PIN, TRGPIN, ANPIN);

		// Start reading
		hlc1395.start();
		ledA = new MPIOSM_DIO(9, true);
		werte=new int[10];
	}

	static
	{
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		// Initialize task
		Task t = new DistSensorTest();
		t.period = 50;
		Task.install(t);
	}
}
