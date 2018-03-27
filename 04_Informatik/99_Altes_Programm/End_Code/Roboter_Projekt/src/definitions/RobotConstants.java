package definitions;

import java.lang.Math;

public class RobotConstants {
	// Constants for tasks
	public static final int MOVE_TASK_PERIOD = 10;
	public static final int ENCODER_TASK_PERIOD = 10;
	public static final int MOTOR_CONTROLLER_TASK_PERIOD = 50;
	// Constants for Communication
	public static final int WIFI_CODE_REQ_START = 11001;
	public static final int WIFI_CODE_ACK_START = 10101;
	public static final int WIFI_CODE_REQ_END = 11002;
	public static final int WIFI_CODE_ACK_END = 10102;
	public static final int WIFI_CODE_REQ_RESET = 11003;
	public static final int WIFI_CODE_ACK_RESET = 10103;
	public static final int WIFI_CODE_REQ_PLATFORM_FOUND = 11011;
	public static final int WIFI_CODE_ACK_PLATFORM_FOUND = 10111;
	public static final int WIFI_CODE_REQ_PLATFORM_NOT_FOUND = 11012;
	public static final int WIFI_CODE_ACK_PLATFORM_NOT_FOUND = 10112;
	public static final int WIFI_CODE_REQ_GOTO_DOCKPOSITION = 11013;
	public static final int WIFI_CODE_ACK_GOTO_DOCKPOSITION = 10113;
	public static final int WIFI_CODE_REQ_START_DOCK = 11014;
	public static final int WIFI_CODE_ACK_START_DOCK = 10114;
	public static final int WIFI_CODE_REQ_START_TURN = 11015;
	public static final int WIFI_CODE_ACK_START_TURN = 10115;
	public static final int WIFI_CODE_REQ_GOTO_PLATFORM = 11016;
	public static final int WIFI_CODE_ACK_GOTO_PLATFORM = 10116;
	public static final int WIFI_CODE_REQ_ERROR_COMMUNICATION = 11091;
	public static final int WIFI_CODE_ACK_ERROR_COMMUNICATION = 10191;
	public static final int WIFI_CODE_REQ_ERROR_FIND_PLATFORM = 11092;
	public static final int WIFI_CODE_ACK_ERROR_FIND_PLATFORM = 10192;
	public static final int WIFI_CODE_REQ_ERROR_DOCK = 11093;
	public static final int WIFI_CODE_ACK_ERROR_DOCK = 10193;
	// Constants for PID
	public static final double K_S = 12/0.12;
	public static final double T_G = 0.12;
	public static final double T_U = 0.03;
	public static final double PID_K_PROPORTIONAL = 0.8;
	public static final double PID_K_INTEGRAL = 0.3;
	public static final double PID_K_DIFFERENTIAL = 0.0;
	// Constants for Sensors
	public static final int SENSOR_PLATFORM_IR_TASK_PERIOD = 100;
	public static final int SENSOR_DOCK_TASK_PERIOD = 100;
	public static final boolean USE_QADCA = true;
	public static final int VALUE_PLATFORM_LED_RED_MAX_SUM = 2800;		//3460
	public static final int VALUE_PLATFORM_LED_RED_MIN_SUM = 600;		//1300
	public static final int VALUE_PLATFORM_LED_RED_POSITION_1_TO_2 = 2300;		//3100
	public static final int VALUE_PLATFORM_LED_RED_POSITION_5_TO_6 = 2300;		//3200
	public static final int VALUE_LED_DOCK_BORDER = 800;
	// Constants for State Machine
	public static final int STATE_MACHINE_TASK_PERIOD = 325;
	public static final int MAX_SEARCH_ROUND=2;
	public static final int BREAK_TIME_FOR_SEARCHING = (2000/STATE_MACHINE_TASK_PERIOD);
	// Constants for Encoder
	public static final int REEL_DIAMETER = 15;
	public static final double CIRCUMFERENCE = REEL_DIAMETER * Math.PI;
	public static final int TICKS_PER_SPIN = 94208;
	public static final double ENCODER_LENGTH_PER_TICK = CIRCUMFERENCE / TICKS_PER_SPIN;
	public static final boolean USE_TPUA = true;
	public static final boolean USE_TPUB = false;
	// Constants for Motor
	public static final double ROTATION_CONSTANT = 565;
	public static final double GEAR_RATIO = 23;
	public static final double MINUTES_TO_SECONDS = 1.0/60.0;
	public static final double MAXIMUM_MOTOR_VOLTAGE = 12;
	public static final double MECHANICAL_MOTOR_CONSTANT = 5.4/1000;
	// SPINS_PER_SECONDS = 4.913
	public static final double SPINS_PER_SECOND = (ROTATION_CONSTANT / GEAR_RATIO) * MAXIMUM_MOTOR_VOLTAGE * MINUTES_TO_SECONDS;
	public static final double MAX_SPEED = Math.PI * REEL_DIAMETER * SPINS_PER_SECOND;
	// tpuTimeBase = 806 [ns]
    // periodPWM = 50 [us]
	public static final int PERIOD_PWM = 50000 / 806;

	private RobotConstants(){}; //private constructor to prevent instantiation

}
