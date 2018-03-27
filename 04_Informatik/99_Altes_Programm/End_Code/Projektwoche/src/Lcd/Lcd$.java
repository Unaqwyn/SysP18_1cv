package Lcd;

import ch.ntb.inf.deep.runtime.mpc555.driver.HD44780U;
import ch.ntb.inf.deep.runtime.ppc32.Task;

public class Lcd$ extends Task{

	static HD44780U lcd;
	
	// deklaration
	static char[]b;
	
	char[]a = {'T','E','A','M',' ','1','0','a','m',' ','R','I','S','S','A'};
	//char[] a = new char[7];
	//char[] a = {'T','E','A','M',' ','1','0'};
	
	
	public Lcd$(){
		
		lcd = HD44780U.getInstance();
		lcd.init(2);
		lcd.onOff(true, false, false);
		lcd.clearDisplay();
	}
	
	public void action(){
		lcd.clearDisplay();
		for(int i = 0; i <15;i++){
			//lcd.setCursor(0, i); // writeChar increments cursor pos
			lcd.writeChar(a[i]);
			if(i==6){
				lcd.writeLn();
			}
		}
	}
	
	static{
		Task t = new Lcd$();
		t.period = 100;
		Task.install(t);
		
		// instanzierung
		b = new char[2];
		// initialisierung
		b[0]='g';
		b[1]='t';
	}
}
