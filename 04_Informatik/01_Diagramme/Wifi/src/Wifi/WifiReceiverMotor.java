package Wifi;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class WifiReceiverMotor extends Task {
	final int resetPin = 11;
	final static short pinZ=15;
	public static MPIOSM_DIO cont;
	private RN131 wifi;
	//private static WifiReceiver task;
	
	private static WifiReceiverMotor sm;
	
	private final boolean useTPUA = true;
	private final int chnL = 0, chnR = 1;
	private final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;
	
	private int currHightimeL, currHightimeR;
	private TPU_PWM pwmL, pwmR;

	

	public WifiReceiverMotor() throws Exception {
		period = 500;
		
		currHightimeL = 0;
		 currHightimeR = 0;
		 pwmL = new TPU_PWM(useTPUA, chnL, pwmPeriod, currHightimeL);
		 pwmR = new TPU_PWM(useTPUA, chnR, pwmPeriod, currHightimeR);

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
			if(type == CmdInt.Type.Cmd) System.out.print(wifi.cmd.getInt());
			if(type == CmdInt.Type.Cmd && (wifi.cmd.getInt()==100 || wifi.cmd.getInt()==111)){
					sm.update(0,0);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==101){
					sm.update(pwmPeriod, 0);
			}
			else if(type == CmdInt.Type.Cmd && wifi.cmd.getInt()==110){
					sm.update(0, pwmPeriod);
			}
		} else {
			cont.set(false);
			System.out.print("\t(not connected)\t");
		}
		System.out.println();
	}
	
	private void update(int hightimeL, int hightimeR) {
		 currHightimeL = hightimeL;
		 currHightimeR = hightimeR;
		 pwmL.update(hightimeL);
		 pwmR.update(hightimeR);

		 System.out.print(hightimeL); System.out.print("\t/\t");
		 System.out.print(hightimeR); System.out.print("\t/\t");
		 System.out.print(pwmPeriod); System.out.println();
		 }

	static {
		// Init SCI1 (19200 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short) 8);
		// Hook SCI1.out on System.out
		System.out = new PrintStream(sci1.out);
		try {
			sm=new WifiReceiverMotor();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Install and start the task

		try {
			WifiReceiverMotor task = new WifiReceiverMotor();
			task.period = 500;
			Task.install(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
