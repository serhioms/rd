package com.prosperica.mc.util;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.mc.PaymentFrequency;


public class UtilMortgageCalendar {
	
	final static public String className = UtilMortgageCalendar.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public Date getNextPayday(int hawManyPayments, PaymentFrequency frequency, Date baseDate){
		return UtilDateTime.addDate(baseDate, hawManyPayments*frequency.periodDays, hawManyPayments*frequency.periodMonth, 0);
	}

	final static public Date getPrevPayday(int hawManyPayments, PaymentFrequency frequency, Date baseDate){
		return getNextPayday(-hawManyPayments, frequency, baseDate);
	}

}


