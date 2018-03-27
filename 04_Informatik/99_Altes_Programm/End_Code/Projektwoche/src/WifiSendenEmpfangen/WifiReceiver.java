package WifiSendenEmpfangen;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;

public class WifiReceiver extends Task{
	final int resetPin = 12;
	private RN131 wifi;
	
	static int zustand;
	
	public static MPIOSM_DIO led1,led2;
	
	public WifiReceiver() throws Exception{
		period = 500;
		
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short)8);
		
		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(resetPin, true));
		
		led1 = new MPIOSM_DIO(10, true);	// LED1 an Pin10 
		led2 = new MPIOSM_DIO(11, true);	// LED2 an Pin11
	}
	
	public void action(){
		System.out.print(wifi.getState().toString());
		
		if(wifi.connected()){
			System.out.print("\t(connected)\t");
		}
		else{
			System.out.print("\t(not connected)\t");
		}
		System.out.println("get comomand");
		CmdInt.Type type = wifi.cmd.readCmd();
		switch(type){
		case None:
			System.out.println("None");
			break;
		case Illegal:
			System.out.println("Illegal");
			break;
		case Cmd:
			System.out.println("Cmd");
			break;
		case Code:
			System.out.println("Code");
			break;
		case Unknown:
			System.out.println("Unknown");
			break;
		default:
			System.out.println("default");
		
		}
		
		if(type == CmdInt.Type.Cmd){
			System.out.print("command=");
			System.out.print(wifi.cmd.getInt());
			zustand = wifi.cmd.getInt();
		}
		System.out.println();
		
		
		led1.set((zustand == 02 || zustand == 11));
		led2.set((zustand == 11 || zustand == 01));
		
		if(zustand == 99){
			System.out.println("\tError\t");
		}
	}
	
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		
		try{
			WifiReceiver task = new WifiReceiver();
			task.period = 500;
			Task.install(task);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
