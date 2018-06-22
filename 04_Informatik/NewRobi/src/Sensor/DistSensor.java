package Sensor;

import ch.ntb.inf.deep.runtime.ppc32.Task;

public class DistSensor extends Task
{
	private int sensorNR;
	private int[] value;
	private double average = 0;
	private boolean obstacle = false;
	private final double k=1.4;
	private int i=0;

	public DistSensor(int i)
	{
		value = new int[10];
		sensorNR = i;
		period=100;
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
		return Sensoren.readSensor(sensorNR);
	}

	public void action()
	{
			if (Sensoren.readSensor(sensorNR) > (average * k) && value[9]!=0)
			{
				obstacle = true;
			}
			average -= (value[i] *0.1);
			value[i] = readSensor();
			average += (value[i] *0.1);
			i++;
			i=i%10;
	}
}
