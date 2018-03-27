 package communication;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import ios.*;
import definitions.PinMap;

/**
* <h1>Communication</h1>
* In this class will signals be sent and received.
* <p>
*
* @author  Janick Hartmann
* @version 1.0
* @since   2017-03-07
*/

public class Communication{
	/**
	* The variables of the class Communication.
	* 
	* RN131 wifi: Driver for wifi
	* I_O_s ios: Variable for class I_O_s
	* int receivedMessage: Value of the current received message
	*/	
	public static RN131 wifi;
	private static IO ios;
	private int receivedMessage;
	
	
	/**
	 * The constructor of the class Communication.
	 * Creates a new instance of RN131 and I_O_s.
	 * 
	 * @throws Exception communicationError 
	 */
	public Communication() throws Exception{
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short)8);
		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(PinMap.WIFI_RESET_PIN, true));
		ios = new IO();
	}
	
	/**
	* This method sends data to the other robot.
	* 
	* @param sendData: Code that will be sent to the other robot.
	*/
	public void sendData(int sendData)
	{
		if(connected()){
			wifi.cmd.writeCmd(sendData);
		}		
	}
	
	/**
	* This method receive data from the other robot.
	* 
	* @return int: Received code from other robot.
	*/
	public int getData()
	{
		if(connected()){
			CmdInt.Type type = wifi.cmd.readCmd();
			if(type == CmdInt.Type.Cmd){
				receivedMessage = wifi.cmd.getInt();
				return receivedMessage;
			}
			else
			{
				return 98;
			}	
		}
		else{
			return 99;
		}
	}	

	/**
	* This method checks the heartbeat and flashes the communication LED.
	* 
	* @return boolean: Returns true if module is connected.
	*/
	public boolean connected() {
		if (wifi.connected()){
			ios.setLedCommunication(!ios.getLedCommunication());
		}
		else{
		}
		return wifi.connected();
	}
	
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Communication");
	}
}
