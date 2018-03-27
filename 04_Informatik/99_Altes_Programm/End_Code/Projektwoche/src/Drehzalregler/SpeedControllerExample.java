package Drehzalregler;





import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysp.lib.SpeedController4DCMotor;

public class SpeedControllerExample extends Task {
	protected static final boolean TPU_A = true;
	protected static final int TPU_PWM_CH0 = 4;
	protected static final int TPU_PWM_CH1 = 5;
	protected static final int TPU_FQD_A = 2;
	
	/* Controller parameters */
	protected static final float ts = 0.001f;
	protected static final float kp = 1f;
	protected static final float tn = 0.01f;
	
	/* Ticks per rotation from encoder datasheet */
	protected static final int ticksPerRotation = 1024;
	protected static final float gearRatio = 23f/1f;
	protected static final float motorVoltage = 12f;
	
	static SpeedController4DCMotor motor;
	public int counter;
	
		public void action() {
			motor.run();
			float p = motor.getActualPosition();
			counter++;
			if(counter>99){
				System.out.println(p);
				counter=0;
			}
		}
		
		static {
			// Create controller
			motor = new SpeedController4DCMotor(ts, TPU_PWM_CH0, TPU_PWM_CH1, TPU_A, TPU_FQD_A,
			TPU_A, ticksPerRotation, motorVoltage, gearRatio, kp, tn);
			
			// Set desired speed
			motor.setDesiredSpeed(0);

			// Initialize task
			Task t = new SpeedControllerExample();
			t.period = (int) (ts * 1000);
			Task.install(t);
			SCI sci1 = SCI.getInstance(SCI.pSCI1);
			sci1.start(9600, SCI.NO_PARITY, (short)8);
			System.out = new PrintStream(sci1.out);
			System.out.print("TestSpeedController");
		}
}