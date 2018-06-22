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
		if (faktor != 0)
		{
			encoder = new Encoder(pinEnc, faktor);
			Task.install(this);
		}
		else if (faktor == 0)
		{
			encoder = null;
		}

		stop();
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
		}
		return false;
	}

	public void toPos(double pos)
	{
		toPos(pos, 30);
	}

	public void toPos(double pos, int speedAbs)
	{
		if (speedAbs < 0)
			speedAbs *= -1;
		if (speedAbs == 0)
			speedAbs = 10;
		if (encoder != null)
		{
			if (getPos() <= pos)
			{
				max = pos;
				forward = true;
				turns = true;
				setSpeed(speedAbs);
			}
			else if (getPos() >= pos)
			{
				min = pos;
				forward = false;
				turns = true;
				setSpeed(-1 * speedAbs);
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

	public boolean turning()
	{
		return turns;
	}

	public void action()
	{
		if (!turns)
		{
			stop();
		}
		if (inPos())
		{
			stop();
			turns = false;
		}
	}
}
