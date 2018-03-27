package Template;
import java.io.*;
/**
* <h1>Titel</h1>
* Beschreibung der Klasse ...
* <p>
*
* @author  xy
* @version 1.0
* @since   2017-02-28
*/
public class template {
   /**
   * Der Konstruktor der Klasse template.
   * 
   */
   public template() {
      //
   }
   
   /**
   * Diese Methode berechnet die Summe von zwei übergebenen Werten.
   * 
   * @param nummerA Dies ist der erste Parameter der Methode summe.
   * @param nummerB Dies ist der zweite Parameter der Methode summe.
   * @return int Rückgabewert der Methode summe ist die Summe.
   */
   public int summe(int nummerA, int nummerB) {
      return nummerA + nummerB;
   }
   /**
   * Diese Methode gibt ein hallo auf die Kommandozeile aus.
   * Es werden keine Paramater definiert.
   * 
   */
   public void halloAusgeben() {
      System.out.println("hallo");
   }

   /**
   * Dies ist die main Methode, welche nichts macht.
   * @param args Nicht verwendet.
   * @exception IOException bei Eingabe-Fehler
   * @see IOException
   */

   public static void main(String args[]) throws IOException {
      //
   }
}