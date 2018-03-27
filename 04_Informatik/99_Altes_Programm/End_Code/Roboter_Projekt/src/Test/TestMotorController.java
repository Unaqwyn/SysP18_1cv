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

public class TestMotorController extends Task{

	private Communication com;
	private IO ios = null;
	private MotorController motorLeft =null;
	private MotorController motorRight =null;
	private MotorController motorFront =null;
	private double receiveMsg = 0;
	private int counter = 0;

	
	public TestMotorController() throws Exception 
	{
		motorLeft = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_LEFT_PWM_MINUS_TPU_PIN, PinMap.ROPE_LEFT_PWM_PLUS_TPU_PIN, PinMap.ROPE_LEFT_ENC_A_TPU_PIN);
		motorRight = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_RIGHT_PWM_MINUS_TPU_PIN, PinMap.ROPE_RIGHT_PWM_PLUS_TPU_PIN, PinMap.ROPE_RIGHT_ENC_A_TPU_PIN);
		motorFront = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN, PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, PinMap.ROPE_FRONT_ENC_A_TPU_PIN);
		period = 500;
		com = new Communication();
		ios = new IO();
		ios.setLRDisableSleep(true);
		ios.setFDisableSleep(true);
		ios.allLedOff();
		ios.setMagnet(true);
		ios.setOptOnOff(true);
		Task.install(this);
	}
	
	public void action(){
//		ios.setMagnet(false);
////		com.sendData((int)motorControllerLeft.encoderPositionInMM);
////		com.sendData((int)motorControllerRight.encoderPositionInMM);
//		com.sendData((int)motorControllerFront.encoderPositionInMM);
//		receiveMsg = com.getData();
//		receiveMsg *= (-1);
////		System.out.println(receiveMsg);
//		if(receiveMsg != 99)
//		{
////			System.out.println("test");
//			//ios.setLRDisableSleep(true);
//			//motorController.setDesiredPositionInMMandTimeInSec(receiveMsg, 5.0);
//			//motorController.setDesiredSpeed(receiveMsg);
////			motorControllerLeft.setDesiredPosition(receiveMsg);
////			motorControllerRight.setDesiredPosition(receiveMsg);
//			motorControllerFront.setDesiredPosition(receiveMsg);
//			//motorController.motor.setSpeed(receiveMsg);
////			counter = 0;
////			motorControllerRight.motor.setQuarterSpeedForward();
//		}
//			
////		if(!ios.getStartSwitch())
////		{
////			ios.setLedSearch(true);
//////			motorControllerLeft.setDesiredPosition(motorControllerLeft.encoderPositionInMM+10);
//////			motorControllerLeft.setDesiredPosition(0);
//////			motorControllerRight.motor.setQuarterSpeedForward();
//////			motorControllerFront.setDesiredPosition(200);
////		}
////		if(!ios.getButtonMotorRight())
////		{
////			motorControllerRight.setDesiredPosition(motorControllerRight.encoderPositionInMM+10);
////		}
////		else
////		{
////			ios.setLedSearch(false);
//////			motorControllerLeft.setDesiredPosition(0);
//////			motorControllerRight.setDesiredPosition(0);
////			motorControllerFront.setDesiredPosition(0);
////		}
////		
//		
//		
////		if(!ios.getButtonMotorRight()){
////			motorControllerLeft.motor.setQuarterSpeedReverse();
////			motorControllerRight.motor.setQuarterSpeedReverse();
////			ios.setLedDock(!ios.getButtonMotorMiddle());
////		}
//		
//		System.out.println(receiveMsg);
////		counter++;
		
		if(!ios.getButtonMotorRight())
		{
				motorRight.setDesiredPosition(motorRight.encoderPositionInMM+5);
		}
		if(!ios.getButtonMotorLeft())
		{
				motorLeft.setDesiredPosition(motorLeft.encoderPositionInMM+5);
		}
		if(!ios.getButtonMotorMiddle())
		{
				motorFront.setDesiredPosition(motorFront.encoderPositionInMM-5);
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