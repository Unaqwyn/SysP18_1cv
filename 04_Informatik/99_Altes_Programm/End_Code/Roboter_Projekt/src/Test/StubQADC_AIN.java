package Test;

import ch.ntb.inf.deep.runtime.mpc555.driver.QADC_AIN;

/**
 * <h1>StubQADC_AIN</h1> The class StubQADC_AIN overrides the static method
 * read of superclass QADC_AIN. This is necessary to use JUnit-tests without hardware.
 * <p>
 *
 * @author Sandro Santoro
 * @version 1.0
 * @since 2017-04-18
 */
public class StubQADC_AIN extends QADC_AIN{
	
	/**
	 * This method overrides method read of superclass QADC_AIN.
	 * 
	 * @return int 255 as manipulated value of sensors.
	 */
	public static short read(boolean b, int x){
		return 255;
	}
}