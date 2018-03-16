package test;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import sensor.DistSensor;

public class TestDistSensor extends Task
{
	final static short pinA = 8, pinB = 9, pinC=10, pinD=11;
	public static MPIOSM_DIO ledA, ledB, ledC, ledD;
	private DistSensor distSensor;

	public TestDistSensor()
	{
		ledA=new MPIOSM_DIO(pinA, true);
		ledB=new MPIOSM_DIO(pinB, true);
		ledC=new MPIOSM_DIO(pinC, true);
		ledD=new MPIOSM_DIO(pinD, true);
		distSensor = new DistSensor();
	}

	public void action()
	{
		ledA.set(distSensor.hindernis(0));
		ledB.set(distSensor.hindernis(1));
		ledC.set(distSensor.hindernis(2));
		ledD.set(distSensor.hindernis(3));
	}

	static
	{

		// SCI1 initialisieren und als Standardausgabe verwenden
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);

		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);

		Task task = new TestDistSensor();
		task.period = 100;
		Task.install(task);
	}
}
