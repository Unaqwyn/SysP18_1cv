package robot;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import communication.Communication;
import definitions.PinMap;
import definitions.RobotConstants;
import ios.*;
import java.io.PrintStream;
import motorController.*;
import sensor.Sensors;

/**
* <h1>Robot</h1>
* This class is the state machine of the robot.
*
*/

public class Robot extends Task {
	/**
	 * The variables of the class Robot.
	 * 
	 * Communication communication: Variable for class Communication
	 * I_O_s ios: Variable for class I_O_s
	 * Sensors sensors: Variable for class Sensors
	 * Motorcontroller motorLeft: Variable for the left motor
	 * Motorcontroller motorRight: Variable for the right motor
	 * Motorcontroller motorFront: Variable for the front motor
	 * int platformPosition: Saves the position of the platform
	 * int counterPositionReachedAwaiting: Counter to give the motor controller time to calculate the new length difference
	 * States state: Beginning state
	 * int ack: Bit to check if it's the first received message
	 */
	
	private static Communication communication;
	private static IO ios;
	private static Sensors sensors;
	private static MotorController motorLeft;
	private static MotorController motorRight;
	private static MotorController motorFront;
	private static int platformPosition=0;
	private static int counterPositionReachedAwaiting=0;
	private States state = States.start;
	private int ack = 0;
	private boolean platformOtherSide = true;
	private boolean firstMessage = true;
	private boolean touchDown = true;
	
	/**
	* The constructor of the class Communication.
	* Creates a new instance of Communication, I_O_s, Sensors and 3 Motors.
	* Initializes the task and set the ios to the appropriate value. 
	* 
	* @throws Exception communicationError 
	*/
	public Robot() throws Exception {
		communication = new Communication();
		ios = new IO();
		sensors = new Sensors();
		motorLeft = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_LEFT_PWM_MINUS_TPU_PIN, PinMap.ROPE_LEFT_PWM_PLUS_TPU_PIN, PinMap.ROPE_LEFT_ENC_A_TPU_PIN);
		motorRight = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_RIGHT_PWM_MINUS_TPU_PIN, PinMap.ROPE_RIGHT_PWM_PLUS_TPU_PIN, PinMap.ROPE_RIGHT_ENC_A_TPU_PIN);
		motorFront = new MotorController(RobotConstants.USE_TPUA, PinMap.ROPE_FRONT_PWM_MINUS_TPU_PIN, PinMap.ROPE_FRONT_PWM_PLUS_TPU_PIN, PinMap.ROPE_FRONT_ENC_A_TPU_PIN);
		period = RobotConstants.STATE_MACHINE_TASK_PERIOD;
		Task.install(this);
		ios.setLRDisableSleep(true);
		ios.setFDisableSleep(true);
		ios.allLedOff();
		ios.setMagnet(false);
	}
	
	/**
	 * This method is to create states.
	 *
	 */
	
	private static enum States {
		
		start,
		searchPosition,
		dockPosition,
		docking,
		tilting,
		landing,
		end
	}
	
	/**
	 * Main method for the states.
	 *
	 */
	
	public void action(){
		int receivedMessage = communication.getData();
		switch(state){
		
		/**
		 * If the start switch is on or the robot gets a message from the other robot, then the robot moves to the search position.
		 *
		 */
		case start:
				ios.setMagnet(false);
				if(!ios.getButtonMotorLeft() && !ios.getStartSwitch())
				{
					nextState(States.searchPosition, 350, 350, 325);
				}
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
				if(!ios.getStartSwitch()){
					if(receivedMessage != RobotConstants.WIFI_CODE_ACK_START){
						communication.sendData(RobotConstants.WIFI_CODE_REQ_START);
					}
					else if(receivedMessage == RobotConstants.WIFI_CODE_ACK_START){
						nextState(States.searchPosition, 350, 350, 325);
					}
				}
				else if(receivedMessage == RobotConstants.WIFI_CODE_REQ_START){
					ack = 1;
					communication.sendData(RobotConstants.WIFI_CODE_ACK_START);
				}
				else if (receivedMessage != RobotConstants.WIFI_CODE_REQ_START && ack == 1){
					nextState(States.searchPosition, 350, 350, 325);
				}

			break;
		
		/**
		 * As soon as the robot reaches the search position, he searches for the platform and moves to the middle position.
		 *
		 */	
		case searchPosition:
			ios.setMagnet(false);
			if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition()) {
				
					platformPosition = sensors.getPlatform();
					if (platformPosition != -1){
						ios.setLedSearch(!ios.getLedSearch());
						if(receivedMessage == RobotConstants.WIFI_CODE_REQ_PLATFORM_FOUND){
							ack = 1;
							platformOtherSide = true;
							communication.sendData(RobotConstants.WIFI_CODE_ACK_PLATFORM_FOUND);
						}
						else if(!ios.getButtonMotorLeft())
						{
							nextState(States.dockPosition, 650, 650, 550);
						}
						else if(receivedMessage == RobotConstants.WIFI_CODE_REQ_PLATFORM_NOT_FOUND){
							ack = 1;
							platformOtherSide = false;
							communication.sendData(RobotConstants.WIFI_CODE_ACK_PLATFORM_NOT_FOUND);
						}
						else if(receivedMessage != RobotConstants.WIFI_CODE_REQ_PLATFORM_NOT_FOUND && ack == 1 && !platformOtherSide){
							nextState(States.dockPosition, 650, 650, 550);
						}
						else if(receivedMessage != RobotConstants.WIFI_CODE_REQ_PLATFORM_FOUND && ack == 1 && platformOtherSide){
							nextState(States.dockPosition, 650, 650, 550);
						}
						else if (receivedMessage == RobotConstants.WIFI_CODE_ACK_PLATFORM_FOUND){
							communication.sendData(0);
							firstMessage = false;
						}
						else if (receivedMessage != RobotConstants.WIFI_CODE_ACK_PLATFORM_FOUND && firstMessage){
							communication.sendData(RobotConstants.WIFI_CODE_REQ_PLATFORM_FOUND);
//							communication.sendData(platformPosition);
						}
					}
					else if(platformPosition == -1){
						ios.setLedSearch(true);
						if(receivedMessage == RobotConstants.WIFI_CODE_REQ_PLATFORM_FOUND){
							ack = 1;
							platformOtherSide = true;
							communication.sendData(RobotConstants.WIFI_CODE_ACK_PLATFORM_FOUND);
						}
						else if(!ios.getButtonMotorLeft())
						{
							nextState(States.dockPosition, 650, 650, 550);
						}
						else if(receivedMessage == RobotConstants.WIFI_CODE_REQ_PLATFORM_NOT_FOUND){
							ack = 1;
							platformOtherSide = false;
							communication.sendData(RobotConstants.WIFI_CODE_ACK_PLATFORM_NOT_FOUND);
						}
						else if(receivedMessage != RobotConstants.WIFI_CODE_REQ_PLATFORM_NOT_FOUND && ack == 1 && !platformOtherSide){
							nextState(States.dockPosition, 650, 650, 550);
							platformPosition = 3;
						}	
						else if(receivedMessage != RobotConstants.WIFI_CODE_REQ_PLATFORM_FOUND && ack == 1 && platformOtherSide){
							nextState(States.dockPosition, 650, 650, 550);
						}	
						else if (receivedMessage == RobotConstants.WIFI_CODE_ACK_PLATFORM_NOT_FOUND){
							communication.sendData(0);
							firstMessage = false;
						}
						else if (receivedMessage != RobotConstants.WIFI_CODE_ACK_PLATFORM_NOT_FOUND && firstMessage){
							communication.sendData(RobotConstants.WIFI_CODE_REQ_PLATFORM_NOT_FOUND);
						}
						
					}
			}
			else{
				communication.sendData(0);
			}
			break;

		/**
		 * As soon as the robot reaches the middle position, he activates the magnets and moves to the dock position.
		 *
		 */		
		case dockPosition:
			ios.setMagnet(true);
			if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition()){
				ios.setLedDock(true);
				
				if(receivedMessage == RobotConstants.WIFI_CODE_ACK_GOTO_DOCKPOSITION){
					nextState(States.docking, 725);
				}
				else if(!ios.getButtonMotorLeft())
				{
					nextState(States.docking, 725);
				}
				else if(receivedMessage != RobotConstants.WIFI_CODE_ACK_GOTO_DOCKPOSITION){
					communication.sendData(RobotConstants.WIFI_CODE_REQ_GOTO_DOCKPOSITION);
				}
			}
			else{
				communication.sendData(0);
			}
			break;
			
		/**
		 * As soon as the robot reaches the dock position, he docks to the other robot.
		 *
		 *
		 */			
		case docking:
			ios.setMagnet(true);
			if(motorFront.reachedDesiredPosition()){
				ios.setLedDock(!ios.getLedDock());
				
				if(sensors.getDock() && receivedMessage == RobotConstants.WIFI_CODE_ACK_START_TURN){
					
					if(platformPosition != -1){
						nextState(States.tilting, 450);
					}
					else if (platformPosition == -1){
						nextState(States.tilting, 300, 300, 300);
					}
				}
				else if (!ios.getButtonMotorLeft())
				{
					if(platformPosition != -1){
						nextState(States.tilting, 450);
					}
					else if (platformPosition == -1){
						nextState(States.tilting, 300, 300, 300);
					}
				}
				else if(receivedMessage != RobotConstants.WIFI_CODE_ACK_START_TURN){
					communication.sendData(RobotConstants.WIFI_CODE_REQ_START_TURN);
				}
			}
			else{
				communication.sendData(0);
			}
		break;

		/**
		 * As soon as the robots are docked, they tilt.
		 *
		 *
		 */	
		case tilting:
			ios.setMagnet(true);
			if(sensors.getDock() && motorFront.reachedDesiredPosition() && motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition()){
				ios.setLedTilt(!ios.getLedTilt());
					if(receivedMessage == RobotConstants.WIFI_CODE_ACK_GOTO_PLATFORM){
						if(platformPosition != -1)
						{
							nextState(States.landing);
						}
						else
						{
							nextState(States.end, 0, 0, 0);
						}
					}
					else if(!ios.getButtonMotorLeft())
					{
						if(platformPosition != -1)
						{
							nextState(States.landing);
						}
						else
						{
							nextState(States.end, 0, 0, 0);
						}
					}
					else if(receivedMessage != RobotConstants.WIFI_CODE_ACK_GOTO_PLATFORM){
						communication.sendData(RobotConstants.WIFI_CODE_REQ_GOTO_PLATFORM);
					}
			}
			else{
				communication.sendData(0);
			}
			break;

		/**
		 * As soon as the robots are tilted, the robot lands on the platform or if the platform is on the other side, the other robot lands.
		 *
		 *
		 */	
			
		case landing:
			ios.setMagnet(true);
			if(motorFront.reachedDesiredPosition()){
				ios.setLedLand(!ios.getLedLand());
					if(platformPosition == 1)
					{						
						motorLeft.setDesiredPosition(570);//530
						motorRight.setDesiredPosition(390);//400
						motorFront.setDesiredPosition(-200);
						if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition() && motorFront.reachedDesiredPosition() && counterPositionReachedAwaiting > 0 && touchDown)
						{
							motorLeft.motor.divider = 12;
							motorRight.motor.divider = 12;
							nextState(States.end, 0, 0, 0);
						}
					}
					else if(platformPosition == 2)
					{						
						motorLeft.setDesiredPosition(530);//420
						motorRight.setDesiredPosition(400);
						motorFront.setDesiredPosition(-200);
						if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition() && motorFront.reachedDesiredPosition() && counterPositionReachedAwaiting > 0)
						{
							motorLeft.motor.divider = 12;
							motorRight.motor.divider = 12;
							nextState(States.end, 0, 0, 0);
						}
					}
					else if(platformPosition == 3)
					{
						motorLeft.motor.divider = 12;
						motorRight.motor.divider = 12;
						nextState(States.end, 0, 0, 0);
					}
					else if(platformPosition == 5)
					{						
						motorLeft.setDesiredPosition(400); 
						motorRight.setDesiredPosition(530); //420
						motorFront.setDesiredPosition(-200);
						if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition() && motorFront.reachedDesiredPosition() && counterPositionReachedAwaiting > 0)
						{
							motorLeft.motor.divider = 12;
							motorRight.motor.divider = 12;
							nextState(States.end, 0, 0, 0);
						}
					}
					else if(platformPosition == 6)
					{						
						motorLeft.setDesiredPosition(390); //400
						motorRight.setDesiredPosition(570); //530
						motorFront.setDesiredPosition(-200);
						if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition() && motorFront.reachedDesiredPosition() && counterPositionReachedAwaiting > 0 && touchDown)
						{
							motorLeft.motor.divider = 12;
							motorRight.motor.divider = 12;
							nextState(States.end, 0, 0, 0);
						}
					}
					counterPositionReachedAwaiting++;
				}
			else{
				communication.sendData(0);
			}
			break;
			
		/**
		 * As soon as the robots reach the platform, they send each other the message to finish.
		 *
		 *
		 */
		
		case end:
			ios.setMagnet(true);
			if(motorLeft.reachedDesiredPosition() && motorRight.reachedDesiredPosition()){
				ios.setLedLand(true);
				if (receivedMessage == RobotConstants.WIFI_CODE_ACK_END){
					communication.sendData(0);
					ios.allLedOff();
				}
				else if (receivedMessage != RobotConstants.WIFI_CODE_ACK_END){
					communication.sendData(RobotConstants.WIFI_CODE_REQ_END);
				}
			}
			else{
				communication.sendData(0);
			} 
			break;
		
		default:
			
			break;
		}
	}
	
	/**
	 * This method is for preparing the next state.
	 * 
	 * @param State: The next state that should be called.
	 *
	 */
	private void nextState(States State){
		communication.sendData(0);
		ack = 0;
		ios.allLedOff();
		state = State;
	}
	
	/**
	 * This method is for preparing the next state.
	 * 
	 * @param State: The next state that should be called.
	 * @param motorFrontPosition: Move with the front motor to this position.
	 *
	 */
	private void nextState(States State, double motorFrontPosition){
		motorFront.setDesiredPosition((-1)*motorFrontPosition);
		nextState(State);
	}
	
	/**
	 * This method is for preparing the next state.
	 * 
	 * @param State: The next state that should be called.
	 * @param motorLeftPosition: Move with the left motor to this position.
	 * @param motorRightPosition: Move with the right motor to this position.
	 *
	 */
	private void nextState(States State, double motorLeftPosition, double motorRightPosition){
		motorLeft.setDesiredPosition(motorLeftPosition);
		motorRight.setDesiredPosition(motorRightPosition);	
		nextState(State);
	}
	
	/**
	 * This method is for preparing the next state.
	 * 
	 * @param State: The next state that should be called.
	 * @param motorLeftPosition: Move with the left motor to this position.
	 * @param motorRightPosition: Move with the right motor to this position.
	 * @param motorFrontPosition: Move with the front motor to this position.
	 *
	 */
	private void nextState(States State, double motorLeftPosition, double motorRightPosition, double motorFrontPosition){
		motorFront.setDesiredPosition((-1)*motorFrontPosition);
		nextState(State, motorLeftPosition, motorRightPosition);
	}
	
	static {		
		
		// Initialize task

		try {
			new Robot();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(19200, SCI.NO_PARITY, (short)8);
		System.out = new PrintStream(sci1.out);
		System.out.print("Roboter");
	}

}