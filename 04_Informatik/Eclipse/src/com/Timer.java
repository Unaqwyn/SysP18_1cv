package com;

public class Timer
{
	private long startZeit; // in Millisekunden
	private int dauer; // in Millisekunden

	public Timer()
	{
		startZeit = System.currentTimeMillis();
	}

	public void starten(int dauerInMilliSekunden)
	{
		startZeit = System.currentTimeMillis();
		dauer = dauerInMilliSekunden;
	}

	public boolean abgelaufen()
	{
		return System.currentTimeMillis() - startZeit >= dauer;
	}

}
