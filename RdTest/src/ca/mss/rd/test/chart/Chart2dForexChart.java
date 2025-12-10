package ca.mss.rd.test.chart;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ca.mss.rd.trade.forex.FX;
import ca.mss.rd.trade.forex.FXIterator;
import ca.mss.rd.trade.forex.FXQuote;
import ca.mss.rd.trader.src.deprecated.ForexSourceOandaOnline;

public class Chart2dForexChart {

	public static void main(String[] args) {
		// Create a chart:
		Chart2D chart = new Chart2D();

		chart.setBackground(Color.WHITE);
		chart.setGridColor(Color.BLACK);

		// Create an ITrace:
		// Note that dynamic charts need limited amount of values!!!
		final ITrace2D trace = new Trace2DLtd(200);
		trace.setColor(Color.RED);

		final ITrace2D trace2 = new Trace2DLtd(200);
		trace2.setColor(Color.BLUE);

		// Add the trace to the chart. This has to be done before adding points
		// (deadlock prevention):
		chart.addTrace(trace);
		chart.addTrace(trace2);

		// Make it visible:
		// Create a frame.
		JFrame frame = new JFrame("Forex Real Time");
		// add the chart to the frame:
		frame.getContentPane().add(chart);
		frame.setSize(400, 300);
		// Enable the termination button [cross on the upper right edge]:
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);

		/*
		 * Now the dynamic adding of points. This is just a demo!
		 * 
		 * Use a separate thread to simulate dynamic adding of date. Note that
		 * you do not have to copy this code. Dynamic charting is just about
		 * adding points to traces at runtime from another thread. Whenever you
		 * hook on to a serial port or some other data source with a polling
		 * Thread (or an event notification pattern) you will have your own
		 * thread that just has to add points to a trace.
		 */

		FXQuote quote = new FXQuote(FX.CurrencyPair.EUR_USD);
		FXIterator fxSource = new ForexSourceOandaOnline(quote);

		long m_starttime = System.currentTimeMillis();

		while( true ) {
			try {
				fxSource.getNextQuotes();
				trace.addPoint(((double) (System.currentTimeMillis() - m_starttime) / 1000.0), quote.ask.doubleValue());
				trace2.addPoint(((double) (System.currentTimeMillis() - m_starttime) / 1000.0), quote.bid.doubleValue());
			} catch (Throwable t) {
			}
		}

	}

}
