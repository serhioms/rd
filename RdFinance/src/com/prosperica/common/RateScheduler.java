package com.prosperica.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateScheduler {

	final public List<CCRate> list;
	
	private RateScheduler() {
		this.list = new ArrayList<CCRate>();
	}
	
	final public void addRate(int nopay, double rate){
		list.add(new CCRate(nopay, rate));
	}
	
	final public void addRate(Date tillDate, double rate){
		list.add(new CCRate(tillDate, rate));
	}
	
	class CCRate {
		final public int nop;
		final public Date tillDate;
		final public double rate;

		private CCRate(int nop, double rate) {
			this.nop = nop;
			this.rate = rate;
			this.tillDate = null;
		}

		private CCRate(Date tillDate, double rate) {
			this.nop = -1;
			this.rate = rate;
			this.tillDate = tillDate;
		}
	}
	

	final static public RateScheduler rates(String... args) {
		RateScheduler rates = new RateScheduler();
		for (int i = 0; i < args.length; i += 2) {
			rates.addRate(Integer.parseInt(args[i + 0]), Double.parseDouble(args[i + 1]));
		}
		return rates;
	}

	final static public RateScheduler rates(Double annualRate) {
		RateScheduler rates = new RateScheduler();
		rates.addRate(Integer.MAX_VALUE, annualRate);
		return rates;
	}

	final static public RateScheduler rates(Date tillDate, Double promoRate, Double annualRate) {
		RateScheduler rates = new RateScheduler();
		rates.addRate(tillDate, promoRate);
		rates.addRate(Integer.MAX_VALUE, annualRate);
		return rates;
	}
	
}
