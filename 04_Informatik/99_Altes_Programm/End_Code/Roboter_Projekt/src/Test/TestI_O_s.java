package Test;

import ios.IO;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class TestI_O_s extends Task {
	private IO iOS;
	
	
	public TestI_O_s() {
		iOS = new IO();
		period = 500;
		Task.install(this);
	}
		

	public void action(){
		//Methods
//		iOS.allLedOff();
//		iOS.setMagnet(false);
//		iOS.setOptOnOff(false);
		switchTest();
		
	}
	
	public void switchAllLedOn(){
		iOS.setLedSearch(true);
		iOS.setLedLand(true);
		iOS.setLedDock(true);
		iOS.setLedCommunication(true);
		iOS.setLedTilt(true);
	}
	
	public void switchAllLedOff(){
		iOS.allLedOff();
	}

	public void switchSearchLedOn(){
		iOS.setLedSearch(true);
	}
	
	public void switchSearchLedOff(){
		iOS.setLedSearch(false);
	}
	
	public void switchLandLedOn(){
		iOS.setLedLand(true);
	}
	
	public void switchLandLedOff(){
		iOS.setLedLand(false);
	}
	
	public void switchDockLedOn(){
		iOS.setLedDock(true);
	}
	
	public void switchDockLedOff(){
		iOS.setLedDock(false);
	}
	
	public void switchCommunicationLedOn(){
		iOS.setLedCommunication(true);
	}
	
	public void switchCommunicationLedOff(){
		iOS.setLedCommunication(false);
	}
	
	public void switchReserveLedOn(){
		iOS.setLedTilt(true);
	}

	public void switchReserveLedOff(){
		iOS.setLedTilt(false);
	}
	public void switchTest(){
		iOS.setLedCommunication(!iOS.getStartSwitch());
		iOS.setLedDock(!iOS.getButtonMotorLeft());
		iOS.setLedLand(!iOS.getButtonMotorMiddle());
		iOS.setLedSearch(!iOS.getButtonMotorRight());
		iOS.setLedTilt(!iOS.getLedTilt());
		
	}
	
	public  void magnetTest(){
		iOS.setMagnet(true);
		
	}
	static {
		new TestI_O_s();
	}
	
	
}
