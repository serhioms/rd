package ca.mss.rd.ssiso.net;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CandlePanel extends JPanel {

	final public static int FLAME_WIDTH = 1;

	private CandleVal[] values;
	private String[] names;
	private String title;

	public CandlePanel(CandleVal[] v, String[] n, String t) {
		names = n;
		values = v;
		title = t;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (values == null || values.length == 0)
			return;
		
		double minValue = 0;
		double maxValue = 0;
		
		for (int i = 0; i < values.length; i++) {
			if (minValue > values[i].min)
				minValue = values[i].min;
			if (maxValue < values[i].max)
				maxValue = values[i].max;
		}

		Dimension d = getSize();
		int clientWidth = d.width;
		int clientHeight = d.height;
		int barWidth = clientWidth / values.length;

		Font titleFont = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
		FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

		int titleWidth = titleFontMetrics.stringWidth(title);
		int y = titleFontMetrics.getAscent();
		int x = (clientWidth - titleWidth) / 2;
		g.setFont(titleFont);
		g.drawString(title, x, y);

		int top = titleFontMetrics.getHeight();
		int bottom = labelFontMetrics.getHeight();
		if (maxValue == minValue)
			return;
		
		double scale = (clientHeight - top - bottom) / (maxValue - minValue);
		y = clientHeight - labelFontMetrics.getDescent();
		g.setFont(labelFont);

		int width, valueX, valueY, height, flameUp, flomeDw;

		for (int i = 0; i < values.length; i++) {
			valueX = i * barWidth + 1;

			valueY = top + (int )((maxValue - values[i].top()) * scale);
			height = (int )(scale*(values[i].top() - values[i].bottom()));
				
			g.setColor(values[i].color());
			g.fillRect(valueX, valueY, width=barWidth, height);
			
			g.setColor(Color.black);
			g.drawRect(valueX, valueY, width, height);

			flameUp = (int )(values[i].flameUp()*scale);
			flomeDw = (int )(values[i].flameDown()*scale);
			
			g.drawRect(x=valueX + (barWidth - FLAME_WIDTH)/2, valueY-flameUp, FLAME_WIDTH, flameUp);
			g.drawRect(x, valueY+height, FLAME_WIDTH, flomeDw);
			
			int labelWidth = labelFontMetrics.stringWidth(names[i]);
			x = i * barWidth + (barWidth - labelWidth) / 2;
			g.drawString(names[i], x, y);
			
			//System.out.println((i+1)+") height="+values[i].height()+" ["+values[i].flameUp()+","+values[i].flameDown()+"]");
		}
	}

	public static void main(String[] argv) throws InterruptedException {

		int candles = 50;

		CandleVal[] values = new CandleVal[candles];
		String[] names = new String[candles];
		
		for (int i = 0; i < names.length; i++) {
			names[i] = Integer.toString(i+1);
		}
		
		for (int i = 0; i < values.length; i++) {
			if( i > 0 )
				values[i] = new CandleVal(values[i-1]);
			else
				values[i] = new CandleVal();
		}

		JFrame f = new JFrame();
		f.setSize(800, 400);
		
		f.getContentPane().add(new CandlePanel(values, names, "Candle Demo"));

		WindowListener wndCloser = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		f.addWindowListener(wndCloser);
		f.setVisible(true);

		// Dynamic

		Random r = new Random();
		for (int i=0; i < 10000; i++) {
			Thread.sleep(100);
			for (int k=1,max=values.length; k<max ; k++) {
				values[k-1].copy(values[k]);
			}
			values[values.length-1].generateRandomNext(values[values.length-1]);
			f.getContentPane().repaint();
		}


	}
}