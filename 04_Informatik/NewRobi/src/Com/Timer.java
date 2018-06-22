package Com;

public class Timer
{
	private long startTime; // in Millisekunden
	private int dauer; // in Millisekunden

	public Timer()
	{
		startTime = System.currentTimeMillis();
	}

	public void start(int time)
	{
		startTime = System.currentTimeMillis();
		dauer = time;
	}

	public boolean lapsed()
	{
		return System.currentTimeMillis() - startTime >= dauer;
	}

}

