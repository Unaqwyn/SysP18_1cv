package Test;

import java.io.PrintStream;
import Com.Wifi;
import Motor.Servo;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestWifi extends Task
{
	private Wifi wifi;
	private Servo servo;
	
	private STATE state;
	
	private static enum STATE
	{
		INIT, CONNECTED
	}

	public TestWifi() throws Exception
	{
		servo = new Servo();
		servo.newPos(1, 2);

			wifi = new Wifi();
			// wifi.init();
			state=STATE.INIT;

	}

	public void action()
	{
			switch(state)
			{
				
				case INIT:
				{
					if(wifi.getReceived()==223)
					{
						state=STATE.CONNECTED;
						wifi.pingStart();
					}
					else
					{
						wifi.sendCmd(222);
					}
				}
				break;
				case CONNECTED:
				{
					System.out.println("connected");
				}
				break;
			}
		
	}

	static
	{
		MPIOSM_DIO wifiIO;
		wifiIO = new MPIOSM_DIO(10, true);
		wifiIO.set(false);
		// 1) Initialize SCI1 (9600 8n1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short) 8);

		// // 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);
	    System.err=new PrintStream(sci1.out);
		try
		{
			Task task = new TestWifi();
			task.period = 1000; // Periodenlänge in ms
			Task.install(task); // Installation des Tasks
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
