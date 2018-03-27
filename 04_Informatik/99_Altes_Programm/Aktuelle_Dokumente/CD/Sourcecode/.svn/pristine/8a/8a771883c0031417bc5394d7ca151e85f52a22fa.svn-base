package Test;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class QuadraturEncoder  extends Task{
	private TPU_FQD encoder = null;
	private long position=0;

	public QuadraturEncoder(boolean inTPUA, int tpuChannel)
	{
		encoder = new TPU_FQD(inTPUA, tpuChannel);
		setPositionZero();
		period = 10;
		Task.install(this);
	}
	
	public void action(){
		position += encoder.getPosition();
		setPositionZero();
	}
	
	public long getPosition()
	{
		return position;
	}
	
	public double getSpeedInTicks()
	{
		return position / period;
	}
	
	public void setZero()
	{
		position=0;
		encoder.setPosition(0);
	}
	
	public void setPositionZero()
	{
		encoder.setPosition(0);
	}
}
