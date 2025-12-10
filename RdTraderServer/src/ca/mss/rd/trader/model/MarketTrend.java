package ca.mss.rd.trader.model;

import java.awt.Color;


public enum MarketTrend {
	NA( Color.WHITE),
	Flat( Color.WHITE),
	Bool( Color.GREEN),
	Bear( Color.RED),
	Both( Color.WHITE);
	
	public final Color color;

	private MarketTrend(Color color) {
		this.color = color;
	}

}
