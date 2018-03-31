package definitions;

public class PinMap {
	//PINs for right rope
	public static final int ROPE_RIGHT_PWM_MINUS_TPU_PIN = 4;
	public static final int ROPE_RIGHT_PWM_PLUS_TPU_PIN = 5;
	public static final int ROPE_RIGHT_ENC_A_TPU_PIN = 2;
	public static final int ROPE_RIGHT_ENC_B_TPU_PIN = 3;
	public static final int LEFT_RIGHT_MOTORS_DISABLE_SLEEP = 30;
	//PINs for left rope
	public static final int ROPE_LEFT_PWM_MINUS_TPU_PIN = 0;
	public static final int ROPE_LEFT_PWM_PLUS_TPU_PIN = 1;
	public static final int ROPE_LEFT_ENC_A_TPU_PIN = 6;
	public static final int ROPE_LEFT_ENC_B_TPU_PIN = 7;
	//PINs for front rope
	public static final int ROPE_FRONT_PWM_MINUS_TPU_PIN = 8;
	public static final int ROPE_FRONT_PWM_PLUS_TPU_PIN = 9;
	public static final int ROPE_FRONT_ENC_A_TPU_PIN = 10;
	public static final int ROPE_FRONT_ENC_B_TPU_PIN = 11;
	public static final int FRONT_MOTOR_DISABLE_SLEEP = 15;
	//PINs for sensors
	public static final int OPT4_AN_PIN = 52;
	public static final int LDR_AN_PIN = 53;
	public static final int OPT3_AN_PIN = 54;
	public static final int OPT1_AN_PIN = 55;
	public static final int OPT2_AN_PIN = 56;
	//PINs for LED, switch and buttons
	public static final int LED5_MDA_PIN = 12;
	public static final int LED2_MPIOB_PIN = 5;
	public static final int LED1_MPIOB_PIN = 6;
	public static final int LED3_MPIOB_PIN = 7;
	public static final int BUTTON3_MOTOR_RIGHT_MPIOB_PIN = 8;
	public static final int LED4_MPIOB_PIN = 9;
	public static final int BUTTON2_MOTOR_MIDDLE_MPIOB_PIN = 10;
	public static final int MAGNET_SWITCH_MPIOB_PIN = 11;
	public static final int BUTTON1_MOTOR_LEFT_MPIOB_PIN = 12;
	public static final int OPT_SWITCH_MPIOB_PIN = 14;	
	public static final int SWITCH1_MPIOB_PIN = 13;
	//PINs for Wifi
	public static final int WIFI_RESET_PIN = 11;
	
	
	private PinMap(){};//private constructor to block instantiation
}