package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ui.CusumCalculatorUI;

public class CusumActionListener implements ActionListener{
	
	private CusumCalculatorUI cusumCalculatorUI;
	private float[] input_data = new float[20]; // observed values
	private float[] oEValues = new float[20]; // observed - expected values
	private float[] cusumvalues = new float[20]; // cusum values
	private float refMean; // expected values
	
	private float refSD;
	private float sigma;
	private float cusum_sd;

	public CusumActionListener(CusumCalculatorUI cusumCalculatorUI) {
		this.cusumCalculatorUI = cusumCalculatorUI;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.cusumCalculatorUI.getBtn_calcCusum()) {
			
			this.cusumCalculatorUI.getTxt_oEValues().setText("");
			this.cusumCalculatorUI.getTxt_cusumValues().setText("");
			
			String[] input = this.cusumCalculatorUI.getTxt_inputValues()
													.getText().split("\n");
			
			this.refMean = Float.valueOf(this.cusumCalculatorUI.getTxt_refMean().getText());
			this.refSD = Float.valueOf(this.cusumCalculatorUI.getTxt_refSD().getText());
			this.sigma = Float.valueOf(this.cusumCalculatorUI.getTxt_sigma().getText());
			this.cusum_sd = this.refSD*this.sigma;
			
			if(input.length<20){
				JOptionPane.showMessageDialog(this.cusumCalculatorUI, "Please enter 20 values");
				System.out.println("Please enter 20 values");
				return;
			} 
			
			for(int i =0;i<input.length;i++){
				this.input_data[i] = Float.valueOf(input[i]);
			}
			
			for(int j=0;j<input_data.length;j++) {
	        	oEValues[j] = input_data[j]-refMean;
	        	this.cusumCalculatorUI.getTxt_oEValues()
	        			.append(String.valueOf(this.oEValues[j])+"\n");
	        }
			
			this.cusumvalues[0] = this.oEValues[0];
	        for(int j=1;j<this.cusumvalues.length;j++) {
	        	this.cusumvalues[j] = this.cusumvalues[j-1]+this.oEValues[j];
	        }
	        
	        for(int j=0;j<this.cusumvalues.length;j++) {
	        	this.cusumCalculatorUI.getTxt_cusumValues()
	        			.append(String.valueOf(this.cusumvalues[j])+"\n");
	        }
	        
	        this.cusum_sd = this.refSD*this.sigma;
	        this.cusumCalculatorUI.getTxt_ucl().setText(String.valueOf(this.cusum_sd));
	        this.cusumCalculatorUI.getTxt_lcl().setText(String.valueOf(-this.cusum_sd));
	        
		} else if(e.getSource()==this.cusumCalculatorUI.getBtn_reset()){
			this.cusumCalculatorUI.resetTextFields();
		}
	}

}
