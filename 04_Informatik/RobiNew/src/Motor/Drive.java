package Motor;

import ch.ntb.inf.deep.runtime.ppc32.Task;

public abstract class Drive extends Task
{
	private Encoder encoder;

	protected boolean turns = false;
	private boolean forward = true;
	private double min = 0;
	private double max;

	public Drive(int pinEnc, double faktor)
	{

		Task.install(this);
		if (faktor != 0)
		{
			encoder = new Encoder(pinEnc, faktor);
		}
		else if(faktor == 0)
		{
			encoder = null;
		}

	}

	// -100 <= speed <= 100
	public abstract void setSpeed(int speed);

	public void stop()
	{
		setSpeed(0);
		turns = false;
	}

	public double getPos()
	{
		if (encoder != null)
		{
			return encoder.getPos();
		}
		return 0.0;
	}

	public boolean inPos()
	{
		if (encoder != null)
		{
			if (!forward)
			{
				return encoder.getPos() <= min;
			}
			else if (forward)
			{
				return encoder.getPos() >= max;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	public void toPos(double pos)
	{
		if (encoder != null)
		{
			if (getPos() <= pos)
			{
				max = pos;
				forward = true;
				turns = true;
				setSpeed(30);
			}
			else if (getPos() >= pos)
			{
				min = pos;
				forward = false;
				turns = true;
				setSpeed(-30);
			}
		}
	}

	public void setEncoderZero()
	{
		if (encoder != null)
		{
			encoder.reset();
		}
	}

	public void action()
	{
		if (!turns)
		{
			stop();
		}
		if (encoder != null)
		{
			if (inPos())
			{
				stop();
				turns = false;
			}
		}
	}
}
