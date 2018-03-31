package Test;

import java.io.PrintStream;

import motorController.*;
import ios.IO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
//import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
//import java.io.PrintStream;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import communication.Communication;
import definitions.PinMap;
import definitions.RobotConstants;

public class TestSpeedController4DCMotorCopy extends Task{

	private Communication com;
	private IO ios = null;	
	private speedController4DCMotorCopy motorControllerLeft =null;
	private int counter=0;
	private double receiveMsg = 0;


	
	public TestSpeedController4DCMotorCopy() throws Exception 
	{
		motorControllerLeft = new speedController4DCMotorCopy(period, PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN, RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_ENC_A_TPU_PIN, RobotConstants.USE_TPUA, RobotConstants.TICKS_PER_SPIN
				, (float)RobotConstants.MAXIMUM_MOTOR_VOLTAGE, (float)RobotConstants.GEAR_RATIO, (float)RobotConstants.PID_K_PROPORTIONAL, (float)0.0054);
		period = 500;
		com = new Communication();
		ios = new IO();
		ios.setLRDisableSleep(false);
		ios.allLedOff();
		ios.setMagnet(false);
		ios.setOptOnOff(false);
		Task.install(this);
	}
	
	public void action(){
		
		com.sendData((int)motorControllerLeft.getActualPosition());
		receiveMsg = com.getData();
		if(receiveMsg != 99)
		{
			ios.setLRDisableSleep(false);
			ios.setFDisableSleep(true);
			motorControllerLeft.setDesiredSpeed((float)receiveMsg);
			
		}
		
	}
	
	
	static{
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		System.out.print("TestMotorController");
		try {
			new TestSpeedController4DCMotorCopy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}