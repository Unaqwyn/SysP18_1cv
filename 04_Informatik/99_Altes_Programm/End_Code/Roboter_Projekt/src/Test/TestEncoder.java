package Test;

import java.io.PrintStream;

import ios.IO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import definitions.PinMap;
import definitions.RobotConstants;
import encoder.Encoder;

public class TestEncoder extends Task{

	private IO ios;
	int counter;
	int countEnc = 0;
	int deltaTicks = 0;
	private Encoder encoderLeft = null;
	private Encoder encoderRight = null;
	private Encoder encoderMiddle = null;
	private long positionLeft=0;
	private double lengthLeft=0;
	private long positionRight=0;
	private double lengthRight=0;	
	private long positionMiddle=0;
	private double lengthMiddle=0;	
	
	public TestEncoder(){
		
		period = 1000;
		encoderLeft = new Encoder(RobotConstants.USE_TPUA, PinMap.ROPE_LEFT_ENC_A_TPU_PIN);
		encoderRight = new Encoder(RobotConstants.USE_TPUA, PinMap.ROPE_RIGHT_ENC_A_TPU_PIN);
		encoderMiddle = new Encoder(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_ENC_A_TPU_PIN);
		encoderLeft.setZero();
		encoderRight.setZero();
		encoderMiddle.setZero();
		ios = new IO();
		ios.setLRDisableSleep(false);
		ios.allLedOff();
		ios.setMagnet(false);
		ios.setOptOnOff(false);
		Task.install(this);
	}
	
	public void action(){
//		positionLeft = encoderLeft.getPosition();
//		System.out.print("position: ");
//		System.out.print(positionLeft);
//		lengthLeft=positionLeft*RobotConstants.ENCODER_LENGTH_PER_TICK;
//		System.out.print("    laenge:   ");
//		System.out.println(lengthLeft);
		
		positionRight = encoderRight.getPosition();
		System.out.print("position: ");
		System.out.print(positionRight);
		lengthRight=positionRight*RobotConstants.ENCODER_LENGTH_PER_TICK;
		System.out.print("    laenge:   ");
		System.out.println(lengthRight);
		
//		positionMiddle = encoderMiddle.getPosition();
//		System.out.print("position: ");
//		System.out.print(positionMiddle);
//		lengthMiddle=positionMiddle*RobotConstants.ENCODER_LENGTH_PER_TICK;
//		System.out.print("    laenge:   ");
//		System.out.println(lengthMiddle);
	}
	
	
	static{
		new TestEncoder();
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		System.out.print("TestEncoder");
	}
}
