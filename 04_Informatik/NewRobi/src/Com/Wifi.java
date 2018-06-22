package Com;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.util.CmdInt;

public class Wifi
{
	public RN131 wifi;
	public int received = 0;
	private MPIOSM_DIO wifiIO;
	private Timer timer;
//	private Timer timer;
	
	public Wifi() throws Exception
	{
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);
		
		
		wifi = new RN131(sci2.in, sci2.out, new MPIOSM_DIO(11, true));
		//wifiIO = new MPIOSM_DIO(10, true);
		//wifiIO.set(false);
		timer = new Timer();
	}

	public void updateReceived()
	{
		//wifiIO.set(wifi.connected());
		if (wifi.connected())
		{
			CmdInt.Type type = wifi.cmd.readCmd();
			received = wifi.cmd.getInt();
			//System.out.print("connected \t");
			//System.out.println(received);
			
		}
//		else {
//			System.out.println("disconnected");
//		}
	}
	
	public int getReceived()
	{
		return received;
	}

	public void sendCmd(int x)
	{
		if (wifi.connected())
			wifi.cmd.writeCmd(x);
	}

	public void pingStart()
	{
		if (wifi.connected())
		{
			wifi.cmd.writeCmd(800);
		}
	}

	public void pingEnd()
	{
		if (wifi.connected())
			wifi.cmd.writeCmd(801);
	}
}