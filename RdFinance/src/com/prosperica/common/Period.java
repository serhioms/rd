package com.prosperica.common;

import com.prosperica.mc.PaymentFrequency;

public class Period {

	final static public int UNLIMITED_AMORTIZATION_PERIOD = 0;
	
	final public int amortization;
	final public PeriodMeasurement period;
	
	public enum PeriodMeasurement {
		Year,
		Month,
		Payment;
	}

	public Period(int amortization, PeriodMeasurement period) {
		this.amortization = amortization;
		this.period = period;
	}
	
	public Period(int amortization) {
		this(amortization, PeriodMeasurement.Year);
	}
	
	public Period() {
		this(UNLIMITED_AMORTIZATION_PERIOD);
	}
	
	final public int getTotalPayments(PaymentFrequency paymentFrequency){
		switch( period ){
		case Year: return paymentFrequency.paymentsPerYear*amortization;
		case Month: return paymentFrequency.paymentsPerMonth*amortization;
		case Payment: return amortization;
		}
		return 0;
	}
	
	final public boolean equals(Period ap){
		return amortization==ap.amortization && period == ap.period;
	}

	final public boolean equalsYear(int years){
		switch( period ){
		case Year: return amortization == years;
		case Month: return amortization == years*12;
		case Payment: return false;
		}
		return true;
	}

	final public int getYears(){
		switch( period ){
		case Year: return amortization;
		case Month: return amortization/12;
		case Payment: return amortization;
		}
		return 0;
	}

	@Override
	public String toString() {
		switch( period ){
		case Year: return amortization+" years";
		case Month: return amortization+" months";
		case Payment: return amortization+" payments";
		}
		return "";
	}
	
	
}
