package Test;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import communication.Communication;
import definitions.RobotConstants;
import ios.IO;

public class TestCommunication extends Task {
	
	private Communication communication;
	private int counter = 0;
	private int sendCounter = 0;
	private RN131.State state =null;
	private static IO ios;
	private int receivedMessage;
	private int ack = 0;
	
	public TestCommunication() throws Exception{
		period = 500;
		Task.install(this);
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("TestCommunication");
		communication =new Communication();
		ios = new IO();
	}
	
	public void action(){
		receivedMessage = communication.getData();
		if(!ios.getStartSwitch()){
			if(receivedMessage != RobotConstants.WIFI_CODE_ACK_START){
				communication.sendData(RobotConstants.WIFI_CODE_REQ_START);
			}
			else if(receivedMessage == RobotConstants.WIFI_CODE_ACK_START){
				communication.sendData(0);
				ack = 0;
			}
		}
		else if(receivedMessage == RobotConstants.WIFI_CODE_REQ_START){
			ack = 1;
			communication.sendData(RobotConstants.WIFI_CODE_ACK_START);
		}
		else if (receivedMessage != RobotConstants.WIFI_CODE_REQ_START && ack == 1){
			ack = 1;
			communication.sendData(0);
		}
		
	}
	
	public void testHeartbeat(){
		
	}

//	public void testConnection(){
////		if(wifi.connected()){
//			if (sendCounter == 0){
//				wifi.sendData(counter);
////				System.out.println(counter);
//				sendCounter = 1;
//			}
//			else{
//				wifi.sendData(counter);
////				System.out.println(counter);
//				sendCounter = 0;
//				counter ++;
//			}
//		}
////	}
//	
//	public void sendStart(){
//		wifi.sendData(RobotConstants.WIFI_CODE_REQ_START);
//	}
//	
//	public void getData(){
//		System.out.println(wifi.getData());
//	}
	
	static {
//		Task t = new TestCommunication();
//		t.period = 500;
//		Task.install(t);
		try {
			new TestCommunication();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}