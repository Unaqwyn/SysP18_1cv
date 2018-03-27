package deepTest;

import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class HelloWorld extends Task{
	
	public static void sayHello(){
		System.out.println("HELLO WORLD");
	}
	
	public static void tannenbaum()
    {
		int höhe = 5;
        for(int x = 1; x <= höhe; x++){
            for(int y = 1; y <= 5-x; y++){
                System.out.print(" ");
            }
            for(int z = 1; z <= 2*x-1; z++){
                System.out.print("*");
            }
            System.out.println();
        }
        for(int x = 1; x < höhe;x++){
            System.out.print(" ");
        }
        System.out.println("|");
    }
	
	public void action(){
		System.out.println("***");
	}
	
	static {
		Task et = new HelloWorld();
		et.period = 1000;
		Task.install(et);
		// 1) Initialize SCI1 (9600 8N1)
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
 
		// 2) Use SCI1 for stdout
		System.out = new PrintStream(sci1.out);
 
		// 3) Say hello to the world
		System.out.println("Sys10 am Rissa");
	}
}
