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

public class BarPanel extends JPanel {
	
	private double[] values;
	private String[] names;
	private String title;

	public BarPanel(double[] v, String[] n, String t) {
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
			if (minValue > values[i])
				minValue = values[i];
			if (maxValue < values[i])
				maxValue = values[i];
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

		for (int i = 0; i < values.length; i++) {
			int valueX = i * barWidth + 1;
			int valueY = top;
			int height = (int) (values[i] * scale);
			if (values[i] >= 0)
				valueY += (int) ((maxValue - values[i]) * scale);
			else {
				valueY += (int) (maxValue * scale);
				height = -height;
			}

			g.setColor(Color.red);
			g.fillRect(valueX, valueY, barWidth - 2, height);
			g.setColor(Color.black);
			g.drawRect(valueX, valueY, barWidth - 2, height);
			int labelWidth = labelFontMetrics.stringWidth(names[i]);
			x = i * barWidth + (barWidth - labelWidth) / 2;
			g.drawString(names[i], x, y);
		}
	}

	public static void main(String[] argv) throws InterruptedException {

		int bars = 10;

		double[] values = new double[bars];
		String[] names = new String[bars];
		
		for (int i = 0; i < names.length; i++) {
			names[i] = Integer.toString(i+1);
		}
		
		for (int i = 0; i < values.length; i++) {
			values[i]= i;
		}

		JFrame f = new JFrame();
		f.setSize(400, 300);
		
		f.getContentPane().add(new BarPanel(values, names, "Bar Demo"));

		WindowListener wndCloser = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		f.addWindowListener(wndCloser);
		f.setVisible(true);

		// Dynamic
		Random r = new Random();
		for (int i = 0; i < 10000; i++) {
			Thread.sleep(10);
			for (int k = 0; k < values.length; k++) {
				values[k] += 3.0 * (r.nextDouble()-0.5);
			}
			f.getContentPane().repaint();
		}

	}
}