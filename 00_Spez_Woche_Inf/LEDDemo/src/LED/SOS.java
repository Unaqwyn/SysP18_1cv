package LED;

import ch.ntb.inf.deep.runtime.mpc555.driver.*;
import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class SOS extends Task{

	final static short pinA=5;
	public static MPIOSM_DIO ledA;
	private int pos=0;
	private int counter=0;
	public static enum STATE{phase1, phase2, phase3, phase4};
	private STATE state = STATE.phase1;
	
	public SOS() {
		ledA=new MPIOSM_DIO(pinA, true);
		ledA.set(false);
		System.out.println("Start");
		
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		Task task = new SOS();
		task.period=500;
		Task.install(task);
	}
	
	public void action()
	{
		if(pos<=8)
		{
			System.out.println(pos);
			switch (state)
			{
			case phase1:
			{
				ledA.set(true);
				System.out.println(ledA.get());
				state=STATE.phase2;
			}
			break;
			case phase2:
			{
				if(pos<=2 || pos>=6)
				{
					ledA.set(false);
					
				}
				System.out.println(ledA.get());
				state=STATE.phase3;
			}
			break;
			case phase3:
			{
				ledA.set(false);
				if(pos==2||pos==8)
				{
					state=STATE.phase4;
					
				}
				else if(pos==5)
				{
					state=STATE.phase4;
					
				}
				else
				{
					state=STATE.phase1;
				}
				System.out.println(ledA.get());
				pos++;
			}
			break;
			case phase4:
			{
				ledA.set(false);
				counter++;
				if(counter==3)
				{
					state=STATE.phase1;
					counter=0;
				}
				System.out.println();
			}
			
			break;
		}
		}
	}

}