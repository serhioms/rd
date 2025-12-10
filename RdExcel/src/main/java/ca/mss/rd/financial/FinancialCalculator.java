package ca.mss.rd.financial;

import ca.mss.rd.excel.ExcelFunctionsDouble;

public class FinancialCalculator {
	final static public String className = FinancialCalculator.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public int DEFAULT_SCALE = 2;
	
	public enum PaymentType {
			Monthly(12, "Monthly"),
			SemiMonthly(24, "Semi monthly"),
			BiWeekly(26, "Bi-Weekly"),
			Weekly(52, "Weekly"),
			AcceleratedBiWeekly(12, "Accelerated Bi-Weekly"),
			AcceleratedWeekly(12, "Accelerated Weekly");
			
			public int paymentFrequency;
			public String descr;
			
			private PaymentType(int paymentFrequency, String descr){
				this.paymentFrequency = paymentFrequency;
				this.descr = descr;
			}
	}

	public enum CompoundType {
			Canadian(2, "Canadian");
			
			public int compoundFrequency;
			public String descr;
			
			private CompoundType(int compoundFrequency, String descr){
				this.compoundFrequency = compoundFrequency;
				this.descr = descr;
			}
	}
	
	final static public double mortgagePayment(CompoundType cp, PaymentType pt, double principal, int amortization, double annualRate, int scale){
		switch( pt ){
		case Monthly:
		case SemiMonthly:
		case BiWeekly:
		case Weekly:
			return ExcelFunctionsDouble.round(-ExcelFunctionsDouble.PMT(ExcelFunctionsDouble.mortgagePeriodicRate(annualRate, cp.compoundFrequency, pt.paymentFrequency), amortization*pt.paymentFrequency, principal), scale);
		case AcceleratedBiWeekly:
			return ExcelFunctionsDouble.round(-ExcelFunctionsDouble.PMT(ExcelFunctionsDouble.mortgagePeriodicRate(annualRate, cp.compoundFrequency, pt.paymentFrequency), amortization*pt.paymentFrequency, principal)/2, scale);
		case AcceleratedWeekly:
			return ExcelFunctionsDouble.round(-ExcelFunctionsDouble.PMT(ExcelFunctionsDouble.mortgagePeriodicRate(annualRate, cp.compoundFrequency, pt.paymentFrequency), amortization*pt.paymentFrequency, principal)/4, scale);
		default:
			new RuntimeException("Payment type not supported ("+pt.descr+")");
			return 0.0;
		}
	}

	final static public double mortgagePayment(CompoundType cp, PaymentType pt, double principal, int amortization, double annualRate){
		return mortgagePayment(cp, pt, principal, amortization, annualRate, DEFAULT_SCALE);
	}

	
	
	
	static private double PRINCIPAL = Double.parseDouble("504000.0");
	static private double ANUAL_RATE = Double.parseDouble("0.0309");
	static private int AMORTIZATION_PHERIOD = 25;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("          Principal,$ = "+PRINCIPAL);
		System.out.println("         Anual Rate,% = "+ANUAL_RATE*100.0);
		System.out.println("       Amortization,Y = "+AMORTIZATION_PHERIOD);

		System.out.println();
		System.out.println("http://www.rmgmortgages.ca/html/RateCal.html Calculations");
		System.out.println("              monthly = 2408.49");
		System.out.println("         semi-monthly = 1203.48");
		System.out.println("            bi-weekly = 1110.85");
		System.out.println("               weekly = 555.26");
		System.out.println("accelerated bi-weekly = 1204.24");
		System.out.println("   accelerated weekly = 602.12");

		System.out.println();
		System.out.println("EXCEL Calculations");
		System.out.println("              monthly = $2,408.49");
		System.out.println("         semi-monthly = $1,203.48");
		System.out.println("            bi-weekly = $1,110.85");
		System.out.println("               weekly = $555.26");
		System.out.println("accelerated bi-weekly = $1,204.25");
		System.out.println("   accelerated weekly = $602.12");

		System.out.println();
		System.out.println("Java(double) Calculations");
		
		PaymentType[] pt = PaymentType.values();
		for(int i=0; i<pt.length; i++){
			System.out.printf("%21s = $%,.2f\n", pt[i].descr, mortgagePayment(CompoundType.Canadian, pt[i], PRINCIPAL, AMORTIZATION_PHERIOD,ANUAL_RATE));
		}
		
		
		System.out.println("\n\nRMG Mortgage Calculations");
		for(int i=0; i<pt.length; i++){
			System.out.printf("%21s = $%,.2f\n", pt[i].descr, mortgagePayment(CompoundType.Canadian, pt[i], 640000.0, 30, 0.0279));
		}
		
	}

}


