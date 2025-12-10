package ca.mss.rd.trader;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import ca.mss.rd.swing.RdApplet;
import ca.mss.rd.trader.server.Trader;
import ca.mss.rd.util.UtilProperty;

public class RdForexTrader extends RdApplet {

	final static public String module = RdForexTrader.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public String APPLICATION_TITLE = "RD F-T";
	static public String APPLICATION_PANEL = "ca.mss.rd.trader.view.TraderMainPanel";
	static public Dimension PREFFERED_SIZE = new Dimension(880, 470);
	static public Boolean MAXIMAZIDE_WHEN_OPEN = false;
	
	static {
		UtilProperty.readConstants(RdForexTrader.class);
	}

	@Override
	protected boolean isMaximizedWhenOpen() {
		return MAXIMAZIDE_WHEN_OPEN;
	}

	@Override
	protected Dimension getPrefferedSize() {
		return PREFFERED_SIZE; 
	}

	@Override
	protected String getAppClazz() {
		return APPLICATION_PANEL;
	}

	@Override
	protected String getAppTitle() {
		return APPLICATION_TITLE;
	}
	
	@Override
	public void exit() {
		Trader.BackOffice.shutdownWkfEngine();
	}

	@Override
	public void init() {
		super.init();
		Trader.BackOffice.startupWkfEngine();
	}

	/*
	 * Run as stand alone
	 */
	public static void main(String args[]) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RdForexTrader().standalone();
			}
		});
	}

}
