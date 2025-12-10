package ca.mss.rd.chart.jfreechart.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class UtilJFreeChart {

	static public final ChartPanel createChartPanel(JFreeChart chart) {
		ChartPanel cp = new ChartPanel(chart, true);
		cp.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createLineBorder(Color.black)));
		return cp;
	}
	
	static public final JFreeChart createChart(String name, TimeSeriesCollection timeseriescollection) {
        DateAxis dateaxis = new DateAxis();
        dateaxis.setTickLabelFont(new Font("SansSerif", 0, 12));
        dateaxis.setLabelFont(new Font("SansSerif", 0, 14));
        dateaxis.setAutoRange(true);
        dateaxis.setLowerMargin(0.0D);
        dateaxis.setUpperMargin(0.0D);
        dateaxis.setTickLabelsVisible(true);

        NumberAxis numberaxis = new NumberAxis();
        numberaxis.setTickLabelFont(new Font("SansSerif", 0, 12));
        numberaxis.setLabelFont(new Font("SansSerif", 0, 14));
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setAutoRange(true);
        numberaxis.setAutoRangeStickyZero(false);
        numberaxis.setAutoRangeIncludesZero(false);
        
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(true, false);
        
        xylineandshaperenderer.setSeriesPaint(0, Color.red);
        xylineandshaperenderer.setSeriesPaint(1, Color.green);
        xylineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F, 0, 2)); 
        xylineandshaperenderer.setSeriesStroke(1, new BasicStroke(3F, 0, 2));
        
        XYPlot xyplot = new XYPlot(timeseriescollection, dateaxis, numberaxis, xylineandshaperenderer);
        
        JFreeChart jfreechart = new JFreeChart(name, new Font("SansSerif", 1, 24), xyplot, true);
        jfreechart.setTitle("");
        
        ChartUtilities.applyCurrentTheme(jfreechart);
        
        jfreechart.setNotify(false);
        jfreechart.setAntiAlias(true);
        
        return jfreechart;
	}
	
	static public final TimeSeries createTrace(String name, int max) {
		TimeSeries trace = new TimeSeries(name);
		trace.setMaximumItemCount(max);
		return trace;
	}

}
