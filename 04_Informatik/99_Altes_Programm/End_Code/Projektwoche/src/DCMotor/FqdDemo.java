package DCMotor;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.inf.deep.runtime.ppc32.Task;


public class FqdDemo extends Task {
	final byte tpuPin = 2;
	
	final boolean useTPUA = true;
	private TPU_FQD fqd;

	public FqdDemo() {
//		sm = new SignMagn();
		fqd = new TPU_FQD(useTPUA, tpuPin);
		period = 100;
		Task.install(this);
	}
	
	public void action() {
//		sm.leftFull();
		int pos = fqd.getPosition();
		System.out.print('>');
		System.out.println(2*Math.PI-Math.abs(pos*2*Math.PI/((3249*4*64)/196)));
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		
		System.out = new PrintStream(sci1.out);
		
		new FqdDemo();	
	}
}