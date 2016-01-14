package ui;

public class CusumController {

	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  CusumCalculatorUI cusumCalc = new CusumCalculatorUI();
		      }
		    });
	}
}
