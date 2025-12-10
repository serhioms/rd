package ca.mss.rd.chart2d.util;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterDate;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.views.ChartPanel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class UtilChart2D {

	@SuppressWarnings("deprecation")
	static public final Chart2D createChart(String name) {
		final ZoomableChart chart = new ZoomableChart();
		chart.getAxisX().setPaintGrid(true);
		chart.getAxisY().setPaintGrid(false);
		chart.getAxisY().setTitle(null);
		chart.setGridColor(Color.DARK_GRAY);
		//chart.getAxisY().setRangePolicy(new RangePolicyMinimumViewport(new Range(-1.0D, +1.0D)));
		chart.getAxisY().setVisible(true);
		chart.setName(name);
		chart.setBackground(Color.black);
		chart.setForeground(Color.GRAY);

		SimpleDateFormat df = new SimpleDateFormat("kk:mm:ss[yyyyy]");
		LabelFormatterDate formatter = new LabelFormatterDate(df);
		chart.getAxisX().setFormatter(formatter);

		chart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( e.getClickCount() == 2 )
					chart.zoomAll();
			}
		});
		
		return chart;
	}
	
	static public final ChartPanel createChartPanel(Chart2D chart) {
		return new ChartPanel(chart);
	}

	static public final ITrace2D createTrace2DLtd(String name, Color color, int max) {
		ITrace2D trace = new Trace2DLtd(max, name);
		trace.setColor(color);
		return trace;
	}
	

}
