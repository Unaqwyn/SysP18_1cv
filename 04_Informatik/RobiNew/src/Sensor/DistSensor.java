package Sensor;

import java.io.PrintStream;

import Definitions.PinMap;
import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class DistSensor extends Task
{
	private int sensorNR;
	private int[] value;
	private double average = 0;
	private boolean obstacle = false;
	private double k = 1.2;

	public DistSensor(int i)
	{
		value = new int[10];
		sensorNR = i;
		Task.install(this);
	}

	public boolean obstacle()
	{
		if (obstacle)
		{
			obstacle = false;
			return true;
		}
		return false;
	}

	public int readSensor()
	{
		return Sensoren.readSensor(sensorNR) + 30;
	}

	public void action()
	{
		for (int i = 0; i < 10; i++)
		{
			if (Sensoren.readSensor(sensorNR) > average * k && readSensor() != 30)
			{
				obstacle = true;
			}
			average -= (value[i] * 0.1);
			value[i] = readSensor();
			average += (value[i] * 0.1);

		}
	}
}
