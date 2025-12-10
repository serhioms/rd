package com.prosperica.common;

import java.util.Locale;


public enum CompoundType {
	
	US_MORTGAGE("US Mortgage", Locale.US, 12),
	CANADIAN_MORTGAGE("Mortgage", Locale.CANADA, 2),
	CANADIAN_LOAN("Loan", Locale.CANADA, 12),
	CREDIT_CARD("Credit Card", Locale.CANADA, 365),
	CREDIT_LINE("Line Of Credit", Locale.CANADA, 12);

	public final String name;
	public final Locale locale;
	public final int conversionPeriods;

	private CompoundType(String name, Locale locale, int conversionPeriods) {
		this.name = name;
		this.locale = locale;
		this.conversionPeriods = conversionPeriods;
	}
	
	public static CompoundType getByName(String name) {
		final CompoundType[] types = values();
		for(CompoundType type : types) {
			if( type.getName().equalsIgnoreCase(name) ) {
				return type;
			}
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public int getConversionPeriods() {
		return conversionPeriods;
	}
	
	public Locale getLocale() {
		return locale;
	}
}