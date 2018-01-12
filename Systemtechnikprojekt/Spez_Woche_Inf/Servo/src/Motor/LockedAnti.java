package Motor;

import java.io.PrintStream;

 import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
 import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

 public class LockedAnti {
 private static LockedAnti la; // Diese Klasse hat eine Instanz von sich selber

 private final short PWMChn = 0;
 private final boolean useTPUA = true;
 private final static int pwmPeriod = 50000 / TPU_PWM.tpuTimeBase;

 private TPU_PWM pwm;

 public LockedAnti() {
 // PWM-Kanal initialisieren
 pwm = new TPU_PWM(useTPUA, PWMChn, pwmPeriod, pwmPeriod/2);
 }

 public static void hightimeFull() {
 la.update(pwmPeriod);
 }

 public static void hightime3_4() {
 la.update(3 * pwmPeriod / 4);
 }

 public static void hightimeHalf() {
 la.update(pwmPeriod / 2);
 }

 public static void hightime1_4() {
 la.update(pwmPeriod / 4);
 }

 public static void hightimeZero() {
 la.update(0);
 }

 public void update(int hightime) {
 pwm.update(hightime);
 System.out.print(hightime);System.out.print("\t/\t"); System.out.print(pwmPeriod); 
 System.out.println();
 }
 
 public void newSpeed(int speed)
 {
	 if(speed==0||speed==3)
	 {
		 hightimeHalf();
	 }
	 else if(speed==1)
	 {
		 hightime1_4();
	 }
	 else if(speed==2)
	 {
		 hightime3_4();
	 }
 }

 static {

 // SCI1 initialisieren und als Standardausgabe verwenden
 // 1) Initialize SCI1 (9600 8N1)
 SCI sci1 = SCI.getInstance(SCI.pSCI1);
 sci1.start(9600, SCI.NO_PARITY, (short)8);

 // 2) Use SCI1 for stdout
 System.out = new PrintStream(sci1.out);

 // Objekt erzeugen
 la = new LockedAnti();

 // Kopfzeile Ausgeben
 System.out.println("Hightime \t/\t Period");
 }
 }
