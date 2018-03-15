package com;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;

public class Wifi extends Task
{

	private static Wifi task;
	private RN131 wifi;
	public static int received = 0;
	private final int pinA=10;
	private MPIOSM_DIO wifiIO;
	private Timer uhr;

	public Wifi() throws Exception
	{
		period = 500;
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);
		wifi = new RN131(sci2.in, sci2.out, null);
		wifiIO=new MPIOSM_DIO(pinA, true);
		wifiIO.set(false);
		uhr=new Timer();
		try
		{
			task = new Wifi();
			Task.install(task);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void action()
	{
		wifiIO.set(wifi.connected());
		if (wifi.connected())
		{
			CmdInt.Type type = wifi.cmd.readCmd();
			received = wifi.cmd.getInt();
		}
	}

	public static void reset()
	{
		task.wifi.reset();
	}

	public static void sendCmd(int x)
	{
		if (task.wifi.connected())
			task.wifi.cmd.writeCmd(x);
	}
	
	public void send()
	{
		int x=received+1;
		for(int i=0;i<3;i++)
		{
			if (task.wifi.connected())
				task.wifi.cmd.writeCmd(x);
		}
		uhr.starten(500);
			while(!uhr.abgelaufen())
			{
				action();
			}
		if(received!=x)
		{
			send();
		}
	}

	public void ping()
	{
		if (task.wifi.connected())
			task.wifi.cmd.writeCmd(800);
	}

	public void end()
	{
		if (task.wifi.connected())
			task.wifi.cmd.writeCmd(802);
	}

	/*
	static
	{
		try
		{
			task = new Wifi();
			Task.install(task);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	*/
}
