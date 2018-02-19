package Wifi;

import java.io.PrintStream;

import Motor.SignMagn;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;

public class WifiReceiverMotor2 extends Task {
	private static SignMagn sm;
	final int resetPin = 11;
	final static short pinZ=15;
	public static MPIOSM_DIO cont;
	private RN131 wifi;
	//private static WifiReceiver task;
	

	public WifiReceiverMotor2() throws Exception {
		period = 500;

		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);

		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(resetPin, true));
		cont=new MPIOSM_DIO(pinZ, true);
		cont.set(false);
		sm=new SignMagn();
	}

	public void action() {
		System.out.print(wifi.getState().toString());
		CmdInt.Type type = wifi.cmd.readCmd();
		if (wifi.connected()) {
			System.out.print("\t(connected)\t");
			cont.set(true);
			if (type == CmdInt.Type.Cmd) System.out.print(wifi.cmd.getInt());
			
			if(type == CmdInt.Type.Cmd && (wifi.cmd.getInt()==100||wifi.cmd.getInt()==111))
			{
				sm.update(0,0);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==110)
			{
				sm.update(sm.getPeriod(), 0);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==101)
			{
				sm.update(0, sm.getPeriod());
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
			WifiReceiverMotor2 task = new WifiReceiverMotor2();
			task.period = 500;
			Task.install(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
