package Com;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import Robi.Robi;

public class Wifi extends Task
{
	
	private static Wifi task;
	private RN131 wifi;
	public static int received = 0;
	private MPIOSM_DIO wifiIO;
	private Timer timer;
	public Wifi() throws Exception
	{
		
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);
		wifi = new RN131(sci2.in, sci2.out, null);
		wifiIO = new MPIOSM_DIO(Definitions.PinMap.pinLedWifi, true);
		wifiIO.set(false);
		install(this);
		timer=new Timer();
	}
	
	public void action()
	{
		getReceived();
	}
	
	public void getReceived()
	{
		wifiIO.set(wifi.connected());
		if(wifi.connected())
		{
			CmdInt.Type type = wifi.cmd.readCmd();
			received = wifi.cmd.getInt();
		}
	}
	
	public void sendCmd(int x)
	{
		if(wifi.connected())
			wifi.cmd.writeCmd(x);
	}
	
	public void sendHeight()
	{
		
		int x = Robi.height + 100;
		if(wifi.connected())
			wifi.cmd.writeCmd(x);
	}
	
	public void pingStart()
	{
		
		if(wifi.connected())
		{
			wifi.cmd.writeCmd(800);
		}
	}
	
	public void pingEnd()
	{
		if(wifi.connected())
			wifi.cmd.writeCmd(802);
	}
	
	public void init()
	{
		if(wifi.connected())
		{
			wifi.cmd.writeCmd(222);
			int x = 222;
			timer.start(1000);
			while(x == 222)
			{
				CmdInt.Type type = wifi.cmd.readCmd();
				x = wifi.cmd.getInt();
				if(timer.lapsed())
				{
					wifi.cmd.writeCmd(222);
					timer.start(1000);
				}
			}
		}
		pingStart();
	}
	
	public boolean next()
	{
		int updatedStone = 100 + Robi.height + 1;
		
		if(updatedStone == received)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Communication");
	}
}