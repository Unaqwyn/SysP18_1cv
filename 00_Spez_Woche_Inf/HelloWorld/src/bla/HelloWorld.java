package bla;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class HelloWorld extends Task{
	public static int i=0;
	public static void sayHello()
	{
		System.out.println("Hello again");
		i++;
	}
	static {
	SCI sci1 = SCI.getInstance(SCI.pSCI1);
	sci1.start(9600,  SCI.NO_PARITY, (short)8);
	
	System.out = new PrintStream(sci1.out);
	
	System.out.println("Hello, world");
		
	HelloWorld task = new HelloWorld();
	task.period=1000;
	Task.install(task);
	}
	
	public void action()
	{
		System.out.println("*");
	}

}
