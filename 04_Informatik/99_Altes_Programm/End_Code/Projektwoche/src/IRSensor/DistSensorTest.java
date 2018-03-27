
package IRSensor;
import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.QADC_AIN;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class DistSensorTest extends Task {
	// I/Os to use:
	private final int ADR3PIN = -1; // not used
	private final int ADR2PIN = -1; // not used
	private final int ADR1PIN = 7; // MPIOB7
	private final int ADR0PIN = 6; // MPIOB6
	private final int TRGPIN = 5; // MPIOB5
	private final int ANPIN = 59; // A:AN59
	private HLC1395Pulsed hlc1395;
	private int sensorNummerMax;
	private int sensorNummerMin;
	private int sensorWertMax;
	private int sensorWertMin;
	private final int pinA = 10;
	private final int pinB = 11;
	private final int pinC = 12;
	private final int pinD = 13;
	private MPIOSM_DIO switchA;
	private MPIOSM_DIO switchB;
	private MPIOSM_DIO switchC;
	private MPIOSM_DIO switchD;
	private QADC_AIN adc;
	
	
	public void action() {
//		switchA = new MPIOSM_DIO (pinA, false);
//		switchB = new MPIOSM_DIO (pinB, false);
//		switchC = new MPIOSM_DIO (pinC, false);
//		switchD = new MPIOSM_DIO (pinD, false);
//		sensorNummerMax = -1;
//		sensorNummerMin = -1;
//		sensorWertMax = 0;
//		sensorWertMin = 1000;
//		for (int i = 0; i < 4; i++) {
//			System.out.print(hlc1395.read(i));
//			System.out.print('\t');
//			if(sensorWertMax<hlc1395.read(i)){
//				sensorWertMax = hlc1395.read(i);
//				sensorNummerMax = i+1;
//		}
//			if(sensorWertMin>hlc1395.read(i)){
//				sensorWertMin = hlc1395.read(i);
//				sensorNummerMin = i+1;
//			}
//		}
//		System.out.println();
//		System.out.print("Min: S");
//		System.out.print(sensorNummerMin);
//		System.out.print(", val = ");
//		System.out.print(sensorWertMin);
//		System.out.print("; Max: S");
//		System.out.print(sensorNummerMax);
//		System.out.print(", val = ");
//		System.out.print(sensorWertMax );
//		System.out.print(";");
//		System.out.println();
//		if(switchA.get()){
//			System.out.print("S1: ");
//			System.out.print(hlc1395.read(0));
//			System.out.println();
//		}
//		if(switchB.get()){
//			System.out.print("S2: ");
//			System.out.print(hlc1395.read(1));
//			System.out.println();
//		}
//		if(switchC.get()){
//			System.out.print("S3: ");
//			System.out.print(hlc1395.read(2));
//			System.out.println();
//		}
//		if(switchD.get()){
//			System.out.print("S4: ");
//			System.out.print(hlc1395.read(3));
//			System.out.println();
//		}
		System.out.println(QADC_AIN.read(true, 59));
		
		
	}
	public DistSensorTest(){
//		hlc1395 = HLC1395Pulsed.getInstance();
//		
//		// Initialize sensors
//		hlc1395.init(ADR3PIN, ADR2PIN, ADR1PIN, ADR0PIN, TRGPIN, ANPIN);
//		
//		// Start reading
//		hlc1395.start();
		QADC_AIN.init(true);
	}
	
	static {
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		
		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);
		
		// Initialize task
		Task t = new DistSensorTest();
		t.period = 1000;
		Task.install(t);
	}
}
