package Test;

import java.io.PrintStream;

import ios.*;
import definitions.PinMap;
import definitions.RobotConstants;
import motorController.MotorController;
import sensor.Sensors;
import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.inf.deep.runtime.mpc555.driver.QADC_AIN;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import communication.Communication;


public class TestSensors extends Task {

	Sensors sensors;
	private IO ios;
	private Communication com;
	private static MotorController motorLeft;
	private static MotorController motorRight;
	private static MotorController motorFront;
	
	
	TestSensors() throws Exception{
		sensors = new Sensors();
		motorLeft = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_LEFT_PWM_MINUS_TPU_PIN, PinMap.ROPE_LEFT_PWM_PLUS_TPU_PIN, PinMap.ROPE_LEFT_ENC_A_TPU_PIN);
		motorRight = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_RIGHT_PWM_MINUS_TPU_PIN, PinMap.ROPE_RIGHT_PWM_PLUS_TPU_PIN, PinMap.ROPE_RIGHT_ENC_A_TPU_PIN);
		motorFront = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN, PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, PinMap.ROPE_FRONT_ENC_A_TPU_PIN);
		com = new Communication();
		ios = new IO();
		period = 500;
		Task.install(this);
		ios.setFDisableSleep(true);
		ios.setLRDisableSleep(true);
		ios.allLedOff();
		ios.setMagnet(false);

	}
	
	public void action(){
		ios.setMagnet(false);
		if(!ios.getButtonMotorRight())
		{
				motorRight.setDesiredPosition(motorRight.encoderPositionInMM+10);
		}
		if(!ios.getButtonMotorLeft())
		{
				motorLeft.setDesiredPosition(motorLeft.encoderPositionInMM+10);
		}
		if(!ios.getStartSwitch())
		{
			motorFront.setDesiredPosition((-1)*150);
			motorLeft.setDesiredPosition(350);
			motorRight.setDesiredPosition(350);
		}
		else if(!ios.getButtonMotorMiddle() && ios.getStartSwitch())
		{
			motorFront.setDesiredPosition(0);
			motorLeft.setDesiredPosition(0);
			motorRight.setDesiredPosition(0);
		}
//		System.out.println("Roboter angedockt:");
//		System.out.print(sensors.getDock());
//		System.out.println("Plattformposition:");
//		System.out.print(sensors.getPlatform());
		getValues();
		ios.setLedDock(sensors.getDock());
		
	}
	

	
	public void getValues(){
		
		com.sendData(QADC_AIN.read(true, 55));
		com.sendData(QADC_AIN.read(true, 56));
		com.sendData(QADC_AIN.read(true, 54));
		com.sendData(QADC_AIN.read(true, 52));
//		com.sendData(QADC_AIN.read(true, 53));
		com.sendData(sensors.getPlatform());
		

	}
	
	static {
		try{
			new TestSensors();
		} catch (Exception e){
			e.printStackTrace();
		}
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		
	}
	
}
