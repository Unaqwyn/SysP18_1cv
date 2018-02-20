package Wifi;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;

public class WifiTransmitterServo extends Task {
	final int resetPin = 11;
	int sendData = 12;
	private boolean send;
	final static short pinZ=15;
	public static MPIOSM_DIO cont;
	//newHighTime: 0-90
	private static int newHighTime=0;

	private static RN131 wifi;

	public WifiTransmitterServo() throws Exception {
		period = 500;

		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);

		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(resetPin, true));
		cont=new MPIOSM_DIO(pinZ, true);
		cont.set(false);
		

	}

	
	public void action() {
		if (wifi.connected())
		{
			cont.set(true);
			
		}
		else cont.set(false);
	}
	
	
	public static void newHighTime()
	{
		if (wifi.connected())
		{
			wifi.cmd.writeCmd(newHighTime);
			
		}
		else cont.set(false);
	}

	static {
		// Init SCI1 (19200 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short) 8);
		// Hook SCI1.out on System.out
		System.out = new PrintStream(sci1.out);
		// Install and start the task
		try {
			WifiTransmitterServo task = new WifiTransmitterServo();
			task.period = 500;
			Task.install(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}