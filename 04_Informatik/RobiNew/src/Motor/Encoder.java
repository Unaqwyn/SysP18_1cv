package Motor;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import java.lang.Math;
import Definitions.PinMap;

public class Encoder extends Task
{
	private TPU_FQD fqd;
	private double pos=0;
	private double faktor=1;
	
	
	public Encoder(int pin, double faktor)
	{
		fqd = new TPU_FQD(PinMap.useTPU_A, pin);
		this.faktor=faktor;
		Task t=new Encoder(pin, faktor);
		t.period=100;
		Task.install(t);
	}
	
	public double getPos()
	{
		return pos;
	}
	
	public void action()
	{
		pos+=(fqd.getPosition()*faktor);
	}
	
	public void reset()
	{
		pos=0;
	}
}
