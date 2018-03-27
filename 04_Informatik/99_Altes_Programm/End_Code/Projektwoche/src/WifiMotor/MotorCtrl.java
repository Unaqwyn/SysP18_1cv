package WifiMotor;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class MotorCtrl extends Task {
	private final int pwmChn = 0;
	private final byte encoderChn = 4;
	private final boolean useTPUA = true;
	private final int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;
	private final int stopHt = pwmPeriod / 2;
	private final int highSpeedHt = pwmPeriod * 3 / 4;
	private final int lowSpeedHt = pwmPeriod * 8 / 14;
	private final int ticksPerRound = 4220;
	private final int tickslowSpeed = ticksPerRound * 45 / 360;
	private boolean done;
	private int lastHightime;
	private short endValue;
	
	private TPU_PWM pwm;
	private TPU_FQD fqd;
	
	public MotorCtrl() {
		period = 1;
		pwm = new TPU_PWM(useTPUA, pwmChn, pwmPeriod, stopHt);
		fqd = new TPU_FQD(useTPUA, encoderChn);
		done = true;
	}
	
	public void action() {
		short diff = (short) (endValue - getPosition());
		if (diff <= 0) {
			stop();
			Task.remove(this);
		} 
		else if (diff > tickslowSpeed) {
			update (highSpeedHt);
		}
		else {
			update (lowSpeedHt);
		}
	}
	
	public void drive(int degree){
		if (done) {
			done = false;
			Task.install(this);
			endValue = (short) (getPosition() + (ticksPerRound * degree) / 360);
		}
	}
	
	public void driveForward() {
		if (done) {
			update (highSpeedHt);
		}
	}
	
	public boolean done() {
		return done;
	}
	
	public void stop(){
		update(stopHt);
		done = true;
	}
	
	public void update (int hightime) {
		if (lastHightime != hightime) {
			pwm.update(hightime);
			lastHightime = hightime;
		}
	}
	
	private short getPosition(){
		return fqd.getPosition();
	}
}
