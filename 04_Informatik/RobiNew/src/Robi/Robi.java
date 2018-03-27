package Robi;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.demo.WifiDemo;
import Motor.Lift;
import Motor.Move;
import Com.Wifi;
import com.Timer;

public class Robi extends Task
{
	private Move move;
	private Lift lift;
	private Wifi wifi;
	private Timer timer1;
	
	public static int height;
	private STATE state = STATE.INITROBI;
	
	private static enum STATE
	{
		INITROBI, DRIVEFORWORD_1, GRAP, DRIVEBACKWORD_1, DRIVEBACKWORD_2, TURNRIGHT, SETLEGO, GRIPPERUP, TURNLEFT, DRIVEFORWORD_2, FINISH
	}
	
	public Robi() throws Exception
	{
		
	}
	
	public void action()
	{
		switch(state)
		{
			case INITROBI:
			{
			}
				break;
			case DRIVEFORWORD_1:
			{
			}
				break;
			case GRAP:
			{
			}
				break;
			case DRIVEBACKWORD_1:
			{
			}
				break;
			case DRIVEBACKWORD_2:
			{
			}
				break;
			case TURNRIGHT:
			{
			}
				break;
			case SETLEGO:
			{
			}
				break;
			case GRIPPERUP:
			{
			}
				break;
			case TURNLEFT:
			{
			}
				break;
			case DRIVEFORWORD_2:
			{
			}
				break;
			case FINISH:
			{
			}
				break;
		}
	}
	
	public void initRobi()
	{
	}
	
	public void driveForword_1()
	{
	}
	
	public void grap()
	{
	}
	
	public void driveBackword_1()
	{
	}
	
	public void dirveBackword_2()
	{
	}
	
	public void turnRight()
	{
	}
	
	public void SetLego()
	{
	}
	
	public void gripperUp()
	{
	}
	
	public void turnLeft()
	{
	}
	
	public void driveForword_2()
	{
	}
	
	public void finish()
	{
	}
	
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
