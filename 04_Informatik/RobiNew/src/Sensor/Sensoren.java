package Sensor;

import Definitions.PinMap;
import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;

public class Sensoren
{

	
	private HLC1395Pulsed hlc1395;
	private final int ADR3PIN = -1; // not used
	private final int ADR2PIN = -1; // not used
	private final int ADR1PIN = PinMap.pinSensB; // MPIOB7
	private final int ADR0PIN = PinMap.pinSensA; // MPIOB6
	private final int TRGPIN = PinMap.pinSensTrig; // MPIOB5
	private final int ANPIN = PinMap.pinSensOut; // A:AN59
	
	public Sensoren()
	{
		hlc1395 = HLC1395Pulsed.getInstance();
		// Initialize sensors
		hlc1395.init(ADR3PIN, ADR2PIN, ADR1PIN, ADR0PIN, TRGPIN, ANPIN); // Start reading
		hlc1395.start();
	}
	
	public boolean obstacle(int sensor)
	{
		return true;
	}
	
	public int readSensor(int sensor)
	{
		return 0;
	}
}
