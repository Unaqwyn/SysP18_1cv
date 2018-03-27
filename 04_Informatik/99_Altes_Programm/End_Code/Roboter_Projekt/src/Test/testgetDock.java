package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import sensor.Sensors;

public class testgetDock {

	@Test
	public void test() {
		
		Sensors sensors = new Sensors();
		
		boolean condition = sensors.getDock();
		
		assertTrue("Roboters docked?", condition);
	}
}
