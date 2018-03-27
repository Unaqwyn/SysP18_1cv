package Test;

import motor.Motor;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import definitions.PinMap;
import ios.IO;

public class TestMotor extends Task{
	private Motor motor;
	private IO ios;
	int counter;
	int hightime;
	private States state = States.rest;
	
	private static enum States{
		fullSpeed,
		rest	
	} 
	
	public TestMotor(){
		counter = 0;
		hightime = 0;
		period = 100;
		Task.install(this);
		ios = new IO();
		ios.setLRDisableSleep(false);
		boolean useTPUAPlus = true;
		int channelAPlus = PinMap.ROPE_RIGHT_PWM_PLUS_TPU_PIN;
		int channelAMinus = PinMap.ROPE_RIGHT_PWM_MINUS_TPU_PIN;
		motor = new Motor(useTPUAPlus, channelAPlus, channelAMinus);
	}
	
	public void action(){
		counter++;
		
		ios.allLedOff();
		ios.setMagnet(false);
		ios.setOptOnOff(false);
		motor.updatePWM();
//		motor.setHightime(hightime / 806);
		
		
		switch(state){
		case rest:
			if(counter >= 5){
				ios.setLRDisableSleep(true);
			}
			if(!ios.getButtonMotorLeft()){
				motor.setHalfSpeedReverse();
				ios.setLedSearch(!ios.getButtonMotorLeft());
			}
			else if(!ios.getButtonMotorRight()){
				motor.setHalfSpeedForward();
				ios.setLedDock(!ios.getButtonMotorMiddle());
			}else if(!ios.getStartSwitch()){
				state =States.fullSpeed;
			}
			else {
				motor.setStop();
			}
			
			break;
		}
		
	}
	
	static {
//		Task t = new TestMotor();
//		t.period = 500;
//		Task.install(t);
		new TestMotor();

	}
	
	public void setSpeed(){
//		motor.setSpeed(0);
//		motor.setHalfSpeedForward();
//		motor.setHalfSpeedReverse();
		motor.setStop();
	}
	
	public void allOff(){
		ios.setLedSearch(false);
		ios.setLedLand(false);
		ios.setLedDock(false);
		ios.setLedCommunication(false);
		ios.setLedTilt(true);
		ios.setMagnet(false);
		
	}
}
