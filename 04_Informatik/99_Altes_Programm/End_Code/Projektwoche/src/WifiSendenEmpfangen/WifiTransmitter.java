package WifiSendenEmpfangen;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class WifiTransmitter extends Task {
	
	final int resetPin = 11;
	int sendData = 12;
	private boolean send;
	private MPIOSM_DIO switch1;
	private MPIOSM_DIO switch2;
	final int pin5 = 5, pin6 = 6;
	private RN131 wifi;
	
	
	public WifiTransmitter() throws Exception{
		period = 500;
		
		SCI sci2 =SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short)8);
		switch1 = new MPIOSM_DIO(pin5, false);
		switch2 = new MPIOSM_DIO(pin6, false);
		
		wifi= new RN131(sci2.in, sci2.out, new MPIOSM_DIO(resetPin, true));
		
	}
	
	public void action(){
		wifi.cmd.writeCmd(sendData);
		if(switch1.get() && switch2.get()){
			wifi.cmd.writeCmd(11);
		}else if(switch1.get() && !switch2.get()){
			wifi.cmd.writeCmd(2);
		}else if(!switch1.get() && switch2.get()){
			wifi.cmd.writeCmd(01);
		}else if(!switch1.get() && !switch2.get()){
			wifi.cmd.writeCmd(00);
		}else{
			wifi.cmd.writeCmd(99);
		}
		
	}
	
	static {
		SCI sci1 =SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		
		try{
			WifiTransmitter task = new WifiTransmitter();
			task.period = 1000;
			Task.install(task);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
