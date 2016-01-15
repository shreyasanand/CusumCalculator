package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import util.TextFieldFilter;
import listener.CusumActionListener;

public class CusumCalculatorUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private String str_headerLabel = "Cusum Calculator";
	
	private JLabel lbl_inputValues;
	private String str_inputValues = "Enter 20 values (each in newline)";
	private JTextArea txt_inputValues;

	private JLabel lbl_oEValues;
	private String str_oEValues = "Observed-Expected values";
	private JTextArea txt_oEValues;
	
	private JLabel lbl_cusumValues;
	private String str_cusumValues = "Cusum values";
	private JTextArea txt_cusumValues;
	
	private JLabel lbl_refMean;
	private String str_refMean = "Enter reference mean";
	private JTextField txt_refMean;
	
	private JLabel lbl_refSD;
	private String str_refSD = "Enter reference std dev";
	private JTextField txt_refSD;
	
	private JLabel lbl_sigma;
	private String str_sigma = "Enter sigma";
	private JTextField txt_sigma;
	
	private JLabel lbl_ucl;
	private String str_ucl = "Upper Control Limit (UCL)";
	private JTextField txt_ucl;

	private JLabel lbl_lcl;
	private String str_lcl = "Lower Control Limit (LCL)";
	private JTextField txt_lcl;
	
	private JButton btn_calcCusum;
	private JButton btn_reset;
	private JButton btn_viewGraph;
	
	private TextFieldFilter filter;
	private CusumActionListener cusumActionListener = new CusumActionListener(this);
	
	private boolean isInputValuesEntered = false;
	private boolean isRefMeanEntered = false;
	private boolean isRefSDEntered = false;
	private boolean isSigmaEntered = false;
	
	public CusumCalculatorUI() {
		this.createAndShowGUI();
		this.addListeners();
	}
	
	private void createAndShowGUI() {
		this.init();
		this.setTitle(this.str_headerLabel);
		
		this.setLayout(new BorderLayout());
		this.add(this.createMainPanel());
		this.pack();
		this.setSize(790, 580);
		this.setVisible(true);
	}
	
	private void addListeners() {
		this.btn_calcCusum.addActionListener(this.cusumActionListener);
		this.btn_reset.addActionListener(this.cusumActionListener);
		this.btn_viewGraph.addActionListener(this.cusumActionListener);
		
		this.txt_inputValues.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txt_inputValues.getText().trim().length() == 0) {
					isInputValuesEntered = false;
					enableOrDisableCalcBtn();
				} else {
					isInputValuesEntered = true;
					enableOrDisableCalcBtn();
				}
			}
		});
		
		
		this.txt_refMean.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txt_refMean.getText().trim().length() == 0) {
					isRefMeanEntered = false;
					enableOrDisableCalcBtn();
				} else {
					isRefMeanEntered = true;
					enableOrDisableCalcBtn();
				}
			}
		});
		
		this.txt_refSD.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txt_refSD.getText().trim().length() == 0) {
					isRefSDEntered = false;
					enableOrDisableCalcBtn();
				} else {
					isRefSDEntered = true;
					enableOrDisableCalcBtn();
				}
			}
		});
		
		this.txt_sigma.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txt_sigma.getText().trim().length() == 0) {
					isSigmaEntered = false;
					enableOrDisableCalcBtn();
				} else {
					isSigmaEntered = true;
					enableOrDisableCalcBtn();
				}
			}
		});
	}

	private void enableOrDisableCalcBtn() {
		if(isInputValuesEntered && isRefMeanEntered && isRefSDEntered && isSigmaEntered) {
			this.btn_calcCusum.setEnabled(true);
		} else {
			this.btn_calcCusum.setEnabled(false);
		}
	}
	
	private void init() {
		this.mainPanel = new JPanel();
		
		this.lbl_inputValues = new JLabel(this.str_inputValues);
		this.txt_inputValues = new JTextArea();
		
		this.lbl_oEValues = new JLabel(this.str_oEValues);
		this.txt_oEValues = new JTextArea();
		
		this.lbl_cusumValues = new JLabel(this.str_cusumValues);
		this.txt_cusumValues = new JTextArea();
		
		this.lbl_refMean = new JLabel(this.str_refMean);
		this.txt_refMean = new JTextField();
		this.filter = new TextFieldFilter(TextFieldFilter.NUMERIC,4);
		this.filter.setNegativeAccepted(false);
		this.txt_refMean.setDocument(this.filter);
		
		this.lbl_refSD = new JLabel(this.str_refSD);
		this.txt_refSD = new JTextField();
		this.filter = new TextFieldFilter(TextFieldFilter.FLOAT);
		this.filter.setNegativeAccepted(false);
		this.filter.setPrecisionForFloat(2);
		this.txt_refSD.setDocument(this.filter);
		
		this.lbl_sigma = new JLabel(this.str_sigma);
		this.txt_sigma = new JTextField();
		this.filter = new TextFieldFilter(TextFieldFilter.FLOAT);
		this.filter.setNegativeAccepted(false);
		this.filter.setPrecisionForFloat(2);
		this.txt_sigma.setDocument(this.filter);
		
		this.lbl_ucl = new JLabel(this.str_ucl);
		this.txt_ucl = new JTextField();
		this.txt_ucl.setEditable(false);
		
		this.lbl_lcl = new JLabel(this.str_lcl);
		this.txt_lcl = new JTextField();
		this.txt_lcl.setEditable(false);
		
		this.btn_calcCusum = new JButton("Calculate");
		this.btn_calcCusum.setEnabled(false);
		this.btn_reset = new JButton("Reset");
		this.btn_viewGraph = new JButton("View Graph");
	}

	private JPanel createMainPanel() { 
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.setPreferredSize(new Dimension(800, 800));
		
		this.mainPanel.add(this.createValuesPanel(),BorderLayout.NORTH);
		this.mainPanel.add(this.createControlsPanel(),BorderLayout.WEST);
		
		return this.mainPanel;
	}
	
	private JPanel createValuesPanel(){
		JPanel thisPanel = new JPanel(new FlowLayout());
		thisPanel.add(this.createInputValuesPanel());
		thisPanel.add(this.createOEValuesPanel());
		thisPanel.add(this.createCusumValuesPanel());
		return thisPanel;
	}

	private JPanel createBtnPanel(JButton btn) {
		JPanel thisPanel = new JPanel();
		thisPanel.add(btn);
		
		return thisPanel;
	}
	
	private JPanel createInputValuesPanel() {
		JPanel thisPanel = new JPanel();
		thisPanel.setLayout(new BorderLayout());
		thisPanel.setPreferredSize(new Dimension(250, 370));
		
        thisPanel.add(this.createInputValuesLblPanel(),BorderLayout.NORTH);
        thisPanel.add(this.createInputValuesTxtPanel(),BorderLayout.WEST);
        
        return thisPanel;
	}
	
	private JPanel createInputValuesLblPanel() {
		JPanel thisPanel = new JPanel(new BorderLayout());
		thisPanel.add(this.lbl_inputValues,BorderLayout.WEST);
		
		return thisPanel;
	}
	
	private JPanel createInputValuesTxtPanel() {
		this.txt_inputValues.setLineWrap(true);
        this.txt_inputValues.setWrapStyleWord(true);
        
        JPanel newPanel = new JPanel();
        newPanel.setPreferredSize(new Dimension(200, 370));
        
        JScrollPane scrollPane = new JScrollPane(this.txt_inputValues);
        scrollPane.setVerticalScrollBarPolicy
        (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        newPanel.setLayout(new BorderLayout(10, 10));
        newPanel.add(scrollPane, BorderLayout.CENTER);
		
		return newPanel;
	}
	
	
	private JPanel createOEValuesPanel() {
		JPanel thisPanel = new JPanel();
		thisPanel.setLayout(new BorderLayout());
		thisPanel.setPreferredSize(new Dimension(250, 370));
        
        thisPanel.add(this.createOEValuesLblPanel(),BorderLayout.NORTH);
        thisPanel.add(this.createOEValuesTxtPanel(),BorderLayout.WEST);
        
        return thisPanel;
	}
	
	
	private JPanel createOEValuesTxtPanel() {
		this.txt_oEValues.setLineWrap(true);
        this.txt_oEValues.setWrapStyleWord(true);
        this.txt_oEValues.setEditable(false);
        
        JPanel newPanel = new JPanel();
        newPanel.setPreferredSize(new Dimension(200, 370));
        
        JScrollPane scrollPane = new JScrollPane(this.txt_oEValues);
        scrollPane.setVerticalScrollBarPolicy
        (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        newPanel.setLayout(new BorderLayout(10, 10));
        newPanel.add(scrollPane, BorderLayout.CENTER);
		
		return newPanel;
	}
	
	
	private JPanel createOEValuesLblPanel() {
		JPanel thisPanel = new JPanel(new BorderLayout());
		thisPanel.add(this.lbl_oEValues,BorderLayout.WEST);
		
		return thisPanel;
	}
	
	private JPanel createCusumValuesPanel() {
		JPanel thisPanel = new JPanel();
		thisPanel.setLayout(new BorderLayout());
		
		thisPanel.setPreferredSize(new Dimension(250, 370));
        
        thisPanel.add(this.createCusumValuesLblPanel(),BorderLayout.NORTH);
        thisPanel.add(this.createCusumValuesTxtPanel(),BorderLayout.WEST);
        
        return thisPanel;
	}
	
	private JPanel createCusumValuesLblPanel() {
		JPanel thisPanel = new JPanel(new BorderLayout());
		thisPanel.add(this.lbl_cusumValues,BorderLayout.WEST);
		
		return thisPanel;
	}
	
	private JPanel createCusumValuesTxtPanel(){
		this.txt_cusumValues.setLineWrap(true);
        this.txt_cusumValues.setWrapStyleWord(true);
        this.txt_cusumValues.setEditable(false);
        
        JPanel newPanel = new JPanel();
        newPanel.setPreferredSize(new Dimension(200, 370));
        
        JScrollPane scrollPane = new JScrollPane(this.txt_cusumValues);
        scrollPane.setVerticalScrollBarPolicy
        (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        newPanel.setLayout(new BorderLayout(10, 10));
        newPanel.add(scrollPane, BorderLayout.CENTER);
        
        return newPanel;
	}
	
	private JPanel createControlsPanel(){
		JPanel thisPanel = new JPanel(new BorderLayout());
		thisPanel.add(createInputCriteriaPanel(),BorderLayout.NORTH);
		return thisPanel;
	}
	
	private JPanel createInputCriteriaPanel(){
		JPanel thisPanel = new JPanel(new GridLayout(0,4,10,5));
		
		thisPanel.add(this.createBorderLayoutNorthPanel(this.lbl_refMean));
		thisPanel.add(this.createBorderLayoutNorthPanel(this.txt_refMean));
		
		thisPanel.add(this.createBorderLayoutNorthPanel(this.lbl_ucl));
		thisPanel.add(this.createBorderLayoutNorthPanel(this.txt_ucl));
		
		thisPanel.add(this.createBorderLayoutNorthPanel(this.lbl_refSD));
		thisPanel.add(this.createBorderLayoutNorthPanel(this.txt_refSD));
		
		thisPanel.add(this.createBorderLayoutNorthPanel(this.lbl_lcl));
		thisPanel.add(this.createBorderLayoutNorthPanel(this.txt_lcl));
		
		thisPanel.add(this.createBorderLayoutNorthPanel(this.lbl_sigma));
		thisPanel.add(this.createBorderLayoutNorthPanel(this.txt_sigma));
		
		thisPanel.add(this.createBtnPanel(this.btn_calcCusum));
		thisPanel.add(this.createBtnPanel(this.btn_reset));
		thisPanel.add(this.createBtnPanel(this.btn_viewGraph));
		
		return thisPanel;
	}
	
	private JPanel createBorderLayoutNorthPanel(Component comp){
		JPanel thisPanel = new JPanel(new BorderLayout());
		thisPanel.add(comp,BorderLayout.NORTH);
		
		return thisPanel;
	}
	
	public void resetTextFields(){
		this.txt_inputValues.setText("");
		this.txt_cusumValues.setText("");
		this.txt_oEValues.setText("");
		this.txt_refMean.setText("");
		this.txt_refSD.setText("");
		this.txt_sigma.setText("");
		this.txt_ucl.setText("");
		this.txt_lcl.setText("");
	}
	
	public JButton getBtn_calcCusum() {
		return btn_calcCusum;
	}

	public void setBtn_calcCusum(JButton btn_calcCusum) {
		this.btn_calcCusum = btn_calcCusum;
	}

	public JButton getBtn_reset() {
		return btn_reset;
	}

	public void setBtn_reset(JButton btn_reset) {
		this.btn_reset = btn_reset;
	}

	public JTextArea getTxt_inputValues() {
		return txt_inputValues;
	}

	public void setTxt_inputValues(JTextArea txt_inputValues) {
		this.txt_inputValues = txt_inputValues;
	}

	public JTextArea getTxt_oEValues() {
		return txt_oEValues;
	}

	public void setTxt_oEValues(JTextArea txt_oEValues) {
		this.txt_oEValues = txt_oEValues;
	}

	public JTextArea getTxt_cusumValues() {
		return txt_cusumValues;
	}

	public void setTxt_cusumValues(JTextArea txt_cusumValues) {
		this.txt_cusumValues = txt_cusumValues;
	}

	public JTextField getTxt_refMean() {
		return txt_refMean;
	}

	public void setTxt_refMean(JTextField txt_refMean) {
		this.txt_refMean = txt_refMean;
	}

	public JTextField getTxt_refSD() {
		return txt_refSD;
	}

	public void setTxt_refSD(JTextField txt_refSD) {
		this.txt_refSD = txt_refSD;
	}

	public JTextField getTxt_sigma() {
		return txt_sigma;
	}

	public void setTxt_sigma(JTextField txt_sigma) {
		this.txt_sigma = txt_sigma;
	}

	public JTextField getTxt_ucl() {
		return txt_ucl;
	}

	public void setTxt_ucl(JTextField txt_ucl) {
		this.txt_ucl = txt_ucl;
	}

	public JTextField getTxt_lcl() {
		return txt_lcl;
	}

	public void setTxt_lcl(JTextField txt_lcl) {
		this.txt_lcl = txt_lcl;
	}
	
	public JButton getBtn_viewGraph() {
		return btn_viewGraph;
	}

	public void setBtn_viewGraph(JButton btn_viewGraph) {
		this.btn_viewGraph = btn_viewGraph;
	}
}