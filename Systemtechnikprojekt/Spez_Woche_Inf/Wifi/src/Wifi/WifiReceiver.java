package Wifi;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;

public class WifiReceiver extends Task {
	final int resetPin = 11;
	final static short pinA=6, pinC=8, pinZ=15;
	public static MPIOSM_DIO ledA, ledC, cont;
	private RN131 wifi;
	//private static WifiReceiver task;
	

	public WifiReceiver() throws Exception {
		period = 500;

		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);

		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(resetPin, true));
		ledA=new MPIOSM_DIO(pinA, true);
		ledA.set(false);
		ledC=new MPIOSM_DIO(pinC, true);
		ledC.set(false);
		cont=new MPIOSM_DIO(pinZ, true);
		cont.set(false);

	}

	public void action() {
		System.out.print(wifi.getState().toString());
		CmdInt.Type type = wifi.cmd.readCmd();
		if (wifi.connected()) {
			System.out.print("\t(connected)\t");
			cont.set(true);
			if (type == CmdInt.Type.Cmd) System.out.print(wifi.cmd.getInt());
			if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==1)
			{
				ledA.set(true);
				ledC.set(false);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==0)
			{
				ledA.set(false);
				ledC.set(false);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==10)
			{
				ledA.set(false);
				ledC.set(true);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==11)
			{
				ledA.set(true);
				ledC.set(true);
			}
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
			WifiReceiver task = new WifiReceiver();
			task.period = 500;
			Task.install(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
