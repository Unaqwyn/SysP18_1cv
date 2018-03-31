package Test;

import motor.Motor;
import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import communication.Communication;
import definitions.PinMap;
import definitions.RobotConstants;
import encoder.Encoder;
import ios.IO;

public class TestPartnerteam  extends Task{

	private Communication com;
	private Motor motorLeft;
	private Motor motorRight;
	private Motor motorFront;
	private Encoder encoderLeft;
	private Encoder encoderRight;
	private Encoder encoderFront;
	
	private IO ios;
	
	private int receiveMsg;
	
	private double lengthLeft;
	private double lengthRight;
	private double lengthFront;
	
	public TestPartnerteam() throws Exception
	{
		motorLeft = new Motor(RobotConstants.USE_TPUA, PinMap.ROPE_LEFT_PWM_PLUS_TPU_PIN, PinMap.ROPE_LEFT_PWM_MINUS_TPU_PIN);
		motorRight = new Motor(RobotConstants.USE_TPUA, PinMap.ROPE_RIGHT_PWM_PLUS_TPU_PIN, PinMap.ROPE_RIGHT_PWM_MINUS_TPU_PIN);
		motorFront = new Motor(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN);
		encoderFront = new Encoder(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_ENC_A_TPU_PIN);
		encoderLeft = new Encoder(RobotConstants.USE_TPUA, PinMap.ROPE_LEFT_ENC_A_TPU_PIN);
		encoderRight = new Encoder(RobotConstants.USE_TPUA, PinMap.ROPE_RIGHT_ENC_A_TPU_PIN);
		com = new Communication();
		ios = new IO();
		ios.setLRDisableSleep(false);
		ios.setFDisableSleep(false);
		ios.setMagnet(false);
		ios.allLedOff();
		period = 500;
		Task.install(this);
	}
	public void action()
	{
		receiveMsg = com.getData();
		
		
		
		switch (receiveMsg) {
		case 51:
			motorsStop();
			break;

		case 52:
			motorLeft.setQuarterSpeedForward();
			motorRight.setQuarterSpeedForward();
			motorFront.setQuarterSpeedForward();
			
			break;
			
		case 53:
			motorLeft.setQuarterSpeedReverse();
			motorRight.setQuarterSpeedReverse();
			motorFront.setQuarterSpeedReverse();
			break;
			
		case 56:
			motorLeft.setHalfSpeedForward();
			motorRight.setHalfSpeedForward();
			motorFront.setHalfSpeedForward();
			break;
			
		case 57:
			motorLeft.setHalfSpeedReverse();
			motorRight.setHalfSpeedReverse();
			motorFront.setHalfSpeedReverse();
			break;
			
		case 60:
			ios.setMagnet(true);
			break;
			
		case 61:
			ios.setMagnet(false);
			
		case 70:
			ios.setFDisableSleep(false);
			ios.setLRDisableSleep(false);
			break;
			
		case 71:
			ios.setLRDisableSleep(true);
			break;
			
		case 72:
			ios.setFDisableSleep(true);
			break;
			
		case 73:
			ios.setFDisableSleep(true);
			ios.setLRDisableSleep(true);
			break;
			
		case 74:
			ios.setFDisableSleep(false);
			break;
			
		case 75:
			ios.setLRDisableSleep(false);
			break;

		default:
			motorLeft.setSpeed((double)receiveMsg);
			motorRight.setSpeed((double)receiveMsg);
			motorFront.setSpeed((double)receiveMsg);	
			break;
		}
		lengthFront = encoderFront.getPosition() * RobotConstants.ENCODER_LENGTH_PER_TICK;
		lengthLeft = encoderLeft.getPosition() * RobotConstants.ENCODER_LENGTH_PER_TICK;
		lengthRight = encoderRight.getPosition() * RobotConstants.ENCODER_LENGTH_PER_TICK;

		
		com.sendData((int)lengthLeft);
		com.sendData((int)lengthRight);
		com.sendData((int)lengthFront);
		
//		System.out.println(receiveMsg);
//	    System.out.print("length Left:  ");
//	    System.out.print(lengthLeft);
//	    System.out.print("    ");
//	    System.out.print("length Right:  ");
//	    System.out.print(lengthRight);
//	    System.out.print("    ");
//	    System.out.print("length Front:  ");
//	    System.out.print(lengthFront);
//	    System.out.println();
	}
	
	public void motorsStop(){
		motorLeft.setStop();
		motorRight.setStop();
		motorFront.setStop();
	}
	static{
		try {
			new TestPartnerteam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		System.out.print("TestPartnerteam");
	}
}