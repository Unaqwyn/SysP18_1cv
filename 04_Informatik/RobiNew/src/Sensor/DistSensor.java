package Sensor;

import java.io.PrintStream;

import Definitions.PinMap;
import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;

public class DistSensor
{
	private int sensorNR;
	private int[] werte;
	
	public DistSensor(int i)
	{
		sensorNR=i;
	}

	public boolean obstacle()
	{
		return true;
	}
	
	public int readSensor()
	{
		return 0;
	}
}
