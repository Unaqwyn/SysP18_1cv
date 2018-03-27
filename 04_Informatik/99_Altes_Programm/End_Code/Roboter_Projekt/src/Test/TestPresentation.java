package Test;



import java.io.PrintStream;

import ios.IO;
import ch.ntb.inf.deep.runtime.mpc555.driver.QADC_AIN;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestPresentation extends Task{

	private IO ios;
	
	
	
	public TestPresentation(){
		QADC_AIN.init(true);
		ios = new IO();
		ios.setFDisableSleep(false);
		ios.setLRDisableSleep(false);
	}
	
	public void action(){
		ios.setLedSearch(!ios.getStartSwitch());
		ios.setOptOnOff(ios.getStartSwitch());
		ios.setLedDock(!ios.getButtonMotorLeft());
		ios.setLedLand(!ios.getButtonMotorMiddle());
		ios.setLedCommunication(!ios.getButtonMotorRight());
		ios.setLedTilt(!ios.getLedTilt());
		ios.setMagnet(!ios.getStartSwitch());
		getValues();
		
	}
	
	public void getValues(){
		System.out.print("LDR:		   ");
		System.out.println(QADC_AIN.read(true, 53));
		
		System.out.print("OPT1         ");
		System.out.println(QADC_AIN.read(true, 55));

		
		System.out.print("OPT2        "); 
		System.out.println(QADC_AIN.read(true, 56));
		
		System.out.print("OPT3        ");
		System.out.println(QADC_AIN.read(true, 54));
		
		System.out.print("OPT4        ");
		System.out.println(QADC_AIN.read(true, 52));
	}
	
	static {

		Task t = new TestPresentation();
		t.period = 500;
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		Task.install(t);

	}
	
}
