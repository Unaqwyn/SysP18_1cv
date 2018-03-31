package Test;

import java.io.PrintStream;

import ios.IO;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.lib.SpeedController4DCMotor;
import communication.Communication;
import definitions.PinMap;
import definitions.RobotConstants;

public class positionController extends Task{
	
	SpeedController4DCMotor speedController;
	IO ios;
	Communication com;
	
	private float absPositionInRad = 0f;
	private float desPositionInRad = 0f;
	private float lengthInRad = 0f;
	private float timeInSec = 0f;
	private float speedInRadPerSec = 0f;
	private float actualSpeedInRadPerSec = 0f;
	private int receiveMsg;
	private int counter = 0;
	
	public positionController() throws Exception
	{
		speedController = new SpeedController4DCMotor( (float)RobotConstants.MOTOR_CONTROLLER_TASK_PERIOD / 1000, 
													   PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, 
													   PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN, 
													   RobotConstants.USE_TPUA, 
													   PinMap.ROPE_FRONT_ENC_A_TPU_PIN, 
													   RobotConstants.USE_TPUA, 
													   RobotConstants.TICKS_PER_SPIN, 
													   (float)RobotConstants.MAXIMUM_MOTOR_VOLTAGE, 
													   (float)RobotConstants.GEAR_RATIO, 
													   (float)1, 
													   (float)RobotConstants.MECHANICAL_MOTOR_CONSTANT);
		com = new Communication();
		ios = new IO();
		ios.setLRDisableSleep(false);
		ios.setFDisableSleep(true);
		ios.setMagnet(false);
		ios.allLedOff();
		period = RobotConstants.MOTOR_CONTROLLER_TASK_PERIOD;
		Task.install(this);
	}
	
	public void action()
	{
		com.sendData((int)absPositionInRad);
		receiveMsg = com.getData();
		if(counter>10 && receiveMsg != 99)
		{
			//setPositionAndTime(receiveMsg, 4f);
			//setSpeed();
			speedController.setDesiredSpeed(receiveMsg);
		}
		
		counter++;
	}
	
	public void setPositionAndTime(float spins, float time)
	{
		desPositionInRad = spins * 2 * (float)Math.PI;
		timeInSec = time;
	}
	
	public void setSpeed()
	{
		absPositionInRad = speedController.getActualPosition();
		lengthInRad = desPositionInRad - absPositionInRad;
		timeInSec -= (period/1000);
		try
		{
			speedController.setDesiredSpeed(lengthInRad / timeInSec);
		}finally
		{
			
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