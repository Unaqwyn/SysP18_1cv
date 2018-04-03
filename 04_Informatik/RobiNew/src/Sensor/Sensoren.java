package Sensor;

import Definitions.PinMap;
import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class Sensoren
{

	
	private static HLC1395Pulsed hlc1395;
	private final int ADR3PIN = -1; // not used
	private final int ADR2PIN = -1; // not used
	private final int ADR1PIN = PinMap.pinSensB; // MPIOB7
	private final int ADR0PIN = PinMap.pinSensA; // MPIOB6
	private final int TRGPIN = PinMap.pinSensTrig; // MPIOB5
	private final int ANPIN = PinMap.pinSensOut; // A:AN59
	private DistSensor[] distSensor;
	private short nSensors=4;
	
	public Sensoren()
	{
		distSensor=new DistSensor[nSensors];
		for(int i=0;i<nSensors;i++)
		{
			distSensor[i]=new DistSensor(i);
		}
		hlc1395 = HLC1395Pulsed.getInstance();
		hlc1395.init(ADR3PIN, ADR2PIN, ADR1PIN, ADR0PIN, TRGPIN, ANPIN); // Start reading
		hlc1395.start();
	}
	// 0 = Front
	// 1 = Back
	public boolean obstacle(int sensor)
	{
		return distSensor[sensor].obstacle();
	}
	
	public static int readSensor(int sensor)
	{
		return hlc1395.read(sensor);
	}

	public boolean threshold(int threshold, int sensor)
	{
		return (threshold<hlc1395.read(sensor));
	}
}
