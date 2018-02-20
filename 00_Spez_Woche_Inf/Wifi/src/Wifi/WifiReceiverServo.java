package Wifi;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class WifiReceiverServo extends Task {
	final int resetPin = 11;
	final static short pinZ=15;
	public static MPIOSM_DIO cont;
	private RN131 wifi;
	//private static WifiReceiver task;
	
	private final int testChannel = 1;
	private final boolean useTPUA = true;
	// pwmPeriod in TimeBase Unit (50�000 ns)
	private final int pwmPeriod = 20000000 / TPU_PWM.tpuTimeBase;

	public static int highTime = 1200000 / TPU_PWM.tpuTimeBase;
	public static int newHighTime=2200000 / TPU_PWM.tpuTimeBase;
	public static int max=2200000 / TPU_PWM.tpuTimeBase;
	public static int min=1200000 / TPU_PWM.tpuTimeBase;
	public static int schrittweite=(max-min)/50;

	private static TPU_PWM pwm;
	private static boolean aus=false;
	public boolean auf=true;
	

	public WifiReceiverServo() throws Exception {
		period = 500;
		pwm = new TPU_PWM(useTPUA, testChannel, pwmPeriod, highTime);

		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);

		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(resetPin, true));
		cont=new MPIOSM_DIO(pinZ, true);
		cont.set(false);

	}

	public void action() {
		System.out.print(wifi.getState().toString());
		CmdInt.Type type = wifi.cmd.readCmd();
		if (wifi.connected()) {
			System.out.print("\t(connected)\t");
			cont.set(true);
			highTime=min+(wifi.cmd.getInt()-100)*schrittweite;
			pwm.update(highTime);
		} else {
			System.out.print("\t(not connected)\t");
			cont.set(false);
		}
		System.out.println();

	}

	static {
		// Init SCI1 (19200 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short) 8);
		// Hook SCI1.out on System.out
		System.out = new PrintStream(sci1.out);
		
		// Install and start the task

		try {
			WifiReceiverServo task = new WifiReceiverServo();
			task.period = 500;
			Task.install(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}