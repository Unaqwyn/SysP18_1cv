package Servo;

import Servo.LEDread;
import Servo.ServoA;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;


public class LEDToServo extends Task{
	private ServoA servo;
	
	public LEDToServo()
	{
		servo=new ServoA();
	}
	
	public void action()
	{
		int k=LEDread.readLED();
		servo.toPos(k);
	}
	
	static {
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600,  SCI.NO_PARITY, (short)8);
		Task task = new LEDToServo();
		task.period=500;
		Task.install(task);
	}
	
	
}
