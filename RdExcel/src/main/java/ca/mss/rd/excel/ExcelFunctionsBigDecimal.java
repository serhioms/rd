package ca.mss.rd.excel;

import java.math.BigDecimal;
import java.math.MathContext;

import abstrasy.libraries.math.rjm.BigDecimalMath;

public class ExcelFunctionsBigDecimal {

	final public static String className = ExcelFunctionsBigDecimal.class.getName();
	final public static long serialVersionUID = className.hashCode();

	final public static BigDecimal ZERO = new BigDecimal("0.0");
	final public static BigDecimal PLUS_ONE = new BigDecimal("1");
	final public static BigDecimal MINUS_ONE = new BigDecimal("-1");
	final public static BigDecimal HUNDRED = new BigDecimal("100.0");

	final public static int PRECISION = 64;
	final public static MathContext ROUND = new MathContext(PRECISION);
	final public static MathContext ROUND_EXCEL = new MathContext(16);

	final public static BigDecimal PMT(BigDecimal interestRate, int numberOfPeriods, BigDecimal principalValue){
		BigDecimal powInterestRateOne = interestRate.add(PLUS_ONE).pow(numberOfPeriods, ROUND);
		BigDecimal powInterestRateOneMinus = powInterestRateOne.add(MINUS_ONE).negate();
		return interestRate.multiply(principalValue).multiply(powInterestRateOne).divide(powInterestRateOneMinus, ROUND);
	}
	
	final public static BigDecimal loanPeriodicRate(BigDecimal annualRate, int compoundFrequency){
		return annualRate.divide(new BigDecimal(compoundFrequency), ROUND);
	}
	
	final public static BigDecimal mortgageAnnualRate(BigDecimal annualRate, int compoundFrequency){
		return loanPeriodicRate(annualRate, compoundFrequency).add(PLUS_ONE).pow(compoundFrequency, ROUND).add(MINUS_ONE);
	}
	
	final public static BigDecimal mortgagePeriodicRate(BigDecimal annualRate, int compoundFrequency, int paymentFrequency){
		BigDecimal oneDevNOP = PLUS_ONE.divide(new BigDecimal(paymentFrequency), ROUND);
		BigDecimal anualRatePlusOne = mortgageAnnualRate(annualRate, compoundFrequency).add(PLUS_ONE);
		// Not implemented in BigDecimal !!!
		// BigDecimal anualRatePlusOnePowOneDevNOP = anualRatePlusOne.pow(oneDevNOP, ROUND);
		BigDecimal anualRatePlusOnePowOneDevNOP = BigDecimalMath.pow(anualRatePlusOne, oneDevNOP, ROUND);
		return anualRatePlusOnePowOneDevNOP.add(MINUS_ONE);
	}


	/**
	 * @param args
	 */
	static BigDecimal LOAN_ANUAL_RATE_BD = new BigDecimal("0.0309");
	static int AMORTIZATION_PHERIOD = 25;
	static BigDecimal PRINCIPAL_BD = new BigDecimal("504000.0");
	 
	public static void main(String[] args) {
		System.out.println("Anual Rate   = "+LOAN_ANUAL_RATE_BD);
		System.out.println("Amortization = "+AMORTIZATION_PHERIOD);
		System.out.println("Principal    = "+PRINCIPAL_BD);

		System.out.println();
		System.out.println("EXCELL");
		System.out.println("loanPeriodicRate     = 0.0154500000");
		System.out.println("mortgageAnnualRate    = 0.031138702499999800000");
		System.out.println("mortgagePeriodicRate = 0.002558578255106080000000000 monthly");
		System.out.println("mortgagePeriodicRate = 0.001278471882375950000000000 semi-monthly");
		System.out.println("mortgagePeriodicRate = 0.000589860976411671000000000 weekly");
		System.out.println("mortgagePeriodicRate = 0.001180069888794930000000000 bi-weekly");
		System.out.println("mortgagePeriodicRate = 0.001278471882375950000000000 accelerated bi-weekly");
		System.out.println("mortgagePeriodicRate = 0.000639031760392594000000000 accelerated weekly");

		System.out.println();
		System.out.println("BigDecimal precision ("+PRECISION+")");
		System.out.println("loanPeriodicRate     = "+loanPeriodicRate(LOAN_ANUAL_RATE_BD, 2));
		System.out.println("mortgageAnnualRate    = "+mortgageAnnualRate(LOAN_ANUAL_RATE_BD, 2));
		System.out.println("mortgagePeriodicRate = "+mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 12)+ "  monthly");
		System.out.println("mortgagePeriodicRate = "+mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 24)+ "  semi-monthly");
		System.out.println("mortgagePeriodicRate = "+mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 52)+ "  weekly");
		System.out.println("mortgagePeriodicRate = "+mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 26)+ "  bi-weekly");
		System.out.println("mortgagePeriodicRate = "+mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 24)+ "  accelerated bi-weekly");
		System.out.println("mortgagePeriodicRate = "+mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 48)+ "  accelerated weekly");

		System.out.println();
		System.out.println("EXCELL PAYMENT");
		System.out.println("PMT = 2,408.49261393327000000000  monthly");
		System.out.println("PMT = 1,203.47700121306000000000 semi-monthly");
		System.out.println("PMT = 555.25986047122400000000 weekly");
		System.out.println("PMT = 1,110.84724706600000000000 bi-weekly");
		System.out.println("PMT = 1,203.47700121306000000000 accelerated bi-weekly");
		System.out.println("PMT = 601.54629701197400000000 accelerated weekly");

		System.out.println();
		System.out.println("BigDecimal payment ("+PRECISION+")");
		System.out.println("PMT = "+PMT(mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 12), AMORTIZATION_PHERIOD*12, PRINCIPAL_BD)+" monthly");
		System.out.println("PMT = "+PMT(mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 24), AMORTIZATION_PHERIOD*24, PRINCIPAL_BD)+" semi-monthly");
		System.out.println("PMT = "+PMT(mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 52), AMORTIZATION_PHERIOD*52, PRINCIPAL_BD)+" weekly");
		System.out.println("PMT = "+PMT(mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 26), AMORTIZATION_PHERIOD*26, PRINCIPAL_BD)+" bi-weekly");
		System.out.println("PMT = "+PMT(mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 24), AMORTIZATION_PHERIOD*24, PRINCIPAL_BD)+" accelerated bi-weekly");
		System.out.println("PMT = "+PMT(mortgagePeriodicRate(LOAN_ANUAL_RATE_BD, 2, 48), AMORTIZATION_PHERIOD*48, PRINCIPAL_BD)+" accelerated weekly");

	}
	
}
