package WifiModul;

import ch.ntb.inf.deep.runtime.util.CmdInt;
import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
//import WLAN.CmdInt;
//import WLAN.RN131;


public class WifiDemo extends Task{

	private static WifiDemo task;
	private RN131 wifi;
	private RN131.State state;
	
	
	public WifiDemo() throws Exception{
		
		period = 500;
		
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short)8);
		
		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(11, true));
		state=wifi.getState();
	}
	
	public void action(){
		RN131.State localState=wifi.getState();
		if(state != localState)
		{			
			System.out.print(wifi.getState().toString());
			state=localState;
		}
		
		if(wifi.connected()){
//			System.out.print("\t(connected)\t");
			System.out.print(".");
		}
		else{
//			System.out.print("\t(not connected)\t");
		}
		CmdInt.Type type = wifi.cmd.readCmd();
		if(type == CmdInt.Type.Cmd){
			System.out.print("command=");
			System.out.print(wifi.cmd.getInt());
		}
		else if(type == CmdInt.Type.Code){
			System.out.print("code=");
			System.out.print(wifi.cmd.getInt());
		}
		else if(type == CmdInt.Type.Unknown){
			System.out.print("unknown(");
			System.out.print(wifi.cmd.getHeader());
			System.out.print(")=");
			System.out.print(wifi.cmd.getInt());
		}
//		System.out.println();
		sendCmd();
	}
	
	public static void reset(){
		task.wifi.reset();
	}
	
	public static void sendCmd(){
		if(task.wifi.connected()){
			task.wifi.cmd.writeCmd(123);
		}
	}
	
	public static void sendCode(){
		if(task.wifi.connected()){
			task.wifi.cmd.writeCmd(CmdInt.Type.Code, 456);
		}
	}
	
	public static void sendOther(){
		if(task.wifi.connected()){
			task.wifi.cmd.writeCmd((byte)0xab, 789);
		}
	}
	
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("WifiDemo");
		
		try{
			task = new WifiDemo();
			Task.install(task);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
