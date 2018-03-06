package motor;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import java.lang.Math;

public class Encoder extends Task {
final byte tpuPin4 = 4;
private short pos=0;
private long absPos=0;
private double deltaPhi;
private int r=12;
private double deltaS;

final boolean useTPUA = true;
private TPU_FQD fqd;

public Encoder(){
fqd = new TPU_FQD(useTPUA, tpuPin4);
period = 2000;
Task.install(this);
}

public void action() {
int pos = fqd.getPosition();
}

public double umrechnen()
{
	double faktor =1;
	absPos+=fqd.getPosition()*faktor;
	return absPos;
}

public boolean heightReached(double height)
{
	boolean finished=false;
	if(umrechnen()>=height) finished=true;
	return finished;
}

static {
// 1) Initialize SCI1 (9600 8N1)
SCI sci1 = SCI.getInstance(SCI.pSCI1);
sci1.start(9600, SCI.NO_PARITY, (short)8);

// 2) Use SCI1 for stdout
System.out = new PrintStream(sci1.out);

new Encoder();
}
}

