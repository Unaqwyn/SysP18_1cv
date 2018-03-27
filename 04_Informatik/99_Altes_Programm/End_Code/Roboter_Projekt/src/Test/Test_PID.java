package Test;

import java.io.PrintStream;

import ios.IO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import communication.Communication;
import definitions.PinMap;
import definitions.RobotConstants;
import motorController.MotorController;

public class Test_PID extends Task{

	private Communication com;
	private IO ios = null;
	private MotorController motorControllerFront =null;
	private double receiveMsg = 0;
	
	
	public Test_PID() throws Exception{
		motorControllerFront = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN, PinMap.ROPE_FRONT_ENC_A_TPU_PIN);
		period = 500;
		com = new Communication();
		ios = new IO();
		ios.setLRDisableSleep(false);
		ios.setFDisableSleep(true);
		ios.allLedOff();
		ios.setMagnet(false );
		ios.setOptOnOff(false);
		Task.install(this);
	}
	public void action(){
		

		com.sendData((int)motorControllerFront.encoderPositionInMM);
		
		receiveMsg = com.getData();
		if(receiveMsg != 99)
		{
			System.out.println("Test");
			motorControllerFront.setDesiredPosition(receiveMsg);
			
		}
	}
	
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		System.out.print("TestMotorController");
		try {
			new TestMotorController();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
