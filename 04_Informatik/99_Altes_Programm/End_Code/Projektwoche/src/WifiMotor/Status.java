package WifiMotor;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class Status extends Task{
	
	public static MPIOSM_DIO led1,led2;
	private static Status task;
	public static enum STATE {off, disconnected, connected, error, sending};
	public static STATE state;
	
	public Status(){
		
		led1 = new MPIOSM_DIO(12, true);
		led2 = new MPIOSM_DIO(13, true);
		state = STATE.off;
	}
	public static Status getInstance(){
		if (task == null){
			task = new Status();
			task.period = 1000;
			Task.install(task);
		}
		return task;
	}
	public void showStatus(){
		
		switch (state) {
		case off:
			led2.set(false);
			break;
		case disconnected:
			led1.set(false);
			led2.set(false);
			break;
		case connected:
			led1.set(true);
			led2.set(false);
			break;
		case error:
			led1.set(!led1.get());
			led2.set(false);
			break;
		case sending:
			led2.set(!led2.get());
			break;
		}
	}
	public void setState(STATE state){
		Status.state = state;
	}
	static {
		task = null;
	}
}
