package Motor;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import Definitions.PinMap;

public class Encoder extends Task
{
	private TPU_FQD fqd;
	private double pos = 0;
	private double faktor = 1;
	
	public Encoder(int pin, double faktor)
	{
		fqd = new TPU_FQD(PinMap.useTPU_A, pin);
		this.faktor = faktor;
		Task.install(this);
		fqd.setPosition(0);
	}
	
	public double getPos()
	{
		return pos;
	}
	
	public void action()
	{
		pos += (fqd.getPosition() * faktor);
		fqd.setPosition(0);
	}
	
	public void reset()
	{
		pos = 0;
	}
}
