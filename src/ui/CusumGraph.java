package ui;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class CusumGraph extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private float[] samples;
	private float[] cusumValues;
	private float ucl;
	private float lcl;

	
	public CusumGraph() {
		
	}
	
	public CusumGraph(float[] samples, float[] cusumValues, float ucl, float lcl) {
		super("Cusum Results");
		this.samples = samples;
		this.cusumValues = cusumValues;
		this.ucl = ucl;
		this.lcl = lcl;
		JFreeChart lineChart = ChartFactory.createXYLineChart(
				"Cusum Results",
		         "Samples","Cusum Values",
		         createDataset(),
		         PlotOrientation.VERTICAL,
		         true,true,false);
		
		XYPlot plot = (XYPlot) lineChart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesPaint(0,Color.black);
	    renderer.setSeriesStroke(0,new BasicStroke(2.0f));
	    plot.setRenderer( renderer ); 
	    
		ValueMarker uclMarker = new ValueMarker(this.ucl);
		uclMarker.setPaint(Color.RED);
		uclMarker.setLabel("UCL");
		uclMarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
		uclMarker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		uclMarker.setStroke(new BasicStroke(2.0f));
		
		ValueMarker lclMarker = new ValueMarker(this.lcl);
		lclMarker.setPaint(Color.RED);
		lclMarker.setLabel("LCL");
		lclMarker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		lclMarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
		lclMarker.setStroke(new BasicStroke(2.0f));
		
		plot.addRangeMarker(uclMarker);
		plot.addRangeMarker(lclMarker);
		
		ChartPanel chartPanel = new ChartPanel( lineChart );
	    chartPanel.setPreferredSize(new java.awt.Dimension(700 ,367));
	    setContentPane(chartPanel);
	}
	
	 private XYSeriesCollection createDataset() {
		 XYSeriesCollection dataset = new XYSeriesCollection();
		 XYSeries cusum = new XYSeries("Samples");
		 for(int i=0;i<samples.length;i++){
			 cusum.add(samples[i], cusumValues[i]);
		 }
		 dataset.addSeries(cusum);
	     return dataset;
	   }
	
}
