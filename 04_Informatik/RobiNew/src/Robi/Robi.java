package Robi;

import java.io.PrintStream;
import com.Timer;
import com.Wifi;
// import LED.SOS.STATE;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.demo.WifiDemo;
import motor.LockedAnti;
import motor.LockedAntiEncoder;
import motor.Servo;

public class Robi extends Task
{

	static
	{
		// Initialize task
		try
		{
			new Robi();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.out.print("Roboter");
	}
}
