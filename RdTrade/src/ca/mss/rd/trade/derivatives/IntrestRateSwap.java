package ca.mss.rd.trade.derivatives;

import java.math.BigDecimal;
import java.util.Date;

import ca.mss.rd.trade.exceptions.WrongDataModelException;

import ca.mss.rd.util.Timing;

public class IntrestRateSwap {

	final static public String module = IntrestRateSwap.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	
	final public String description;

	public IntrestRateSwap() {
		this.description = toString();
	}

	public IntrestRateSwap(String description) {
		this.description = description;
	}

	/*
	 * http://en.wikipedia.org/wiki/Interest_rate_swap
	 */
	
	final public String definition(){
		return	"An interest rate swap (IRS) is a popular and highly liquid financial derivative instrument in which two parties " +
				"agree to exchange interest rate cash flows, based on a specified notional amount from a fixed rate to a floating " +
				"rate (or vice versa) or from one floating rate to another. Interest rate swaps are commonly used for both hedging " +
				"and speculating.";
	}

	final public String acronim(){
		return "IRS";
	}

	final public String name(){
		return "Interest Rate Swap";
	}

	/*
	 * In an interest rate swap, each counterparty agrees to pay either a fixed or floating rate denominated in a particular currency 
	 * to the other counterparty. The fixed or floating rate is multiplied by a notional principal amount (say, USD 1 million) and an 
	 * accrual factor given by the appropriate day count convention. When both legs are in the same currency, this notional amount is 
	 * typically not exchanged between counterparties, but is used only for calculating the size of cashflows to be exchanged. When the 
	 * legs are in different currencies, the respective notional amount are typically exchanged at the start and the end of the swap.
	 */
	
	public class Counterparty {
		final public String name;
		
		public Counterparty(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
		
		
	}
	
	public enum DayCountConvention {
		X,Y;

		public BigDecimal getAccrualFactor(){
			return new BigDecimal(1.0);
		}
	}
	
	DayCountConvention dayCountConvention;
	
	public enum Currency {
		USD,EUR,CAD,JPN,GBR;
	}

	public enum FFType {
		Fixed,
		Floating;
	}
	
	public class Leg {
		final public Counterparty counterparty;
		final public Rate rate;
		final public BigDecimal notionalAmount;
		final public Currency currency;
		
		public Leg(Counterparty counterparty, Rate rate, BigDecimal notionalAmount, Currency currency) {
			this.counterparty = counterparty;
			this.rate = rate;
			this.notionalAmount = notionalAmount;
			this.currency = currency;
		}

		@Override
		public String toString() {
			return "["+counterparty+"]["+rate+"][notional="+currency+notionalAmount+"]";
		}
	}

	public Leg fixedLeg, floatingLeg;
	
	public Date startSwap, endSwap;
	
	public BigDecimal notionalAmountExchangeA(){
		if( fixedLeg.currency == fixedLeg.currency )
			return null;
		else
			return fixedLeg.notionalAmount;
	}
	
	public BigDecimal notionalAmountExchangeB(){
		if( floatingLeg.currency == floatingLeg.currency )
			return null;
		else
			return floatingLeg.notionalAmount;
	}
	
	
	/*
	 * The most common interest rate swap is one where one counterparty A pays a fixed rate (the swap rate) to counterparty B, 
	 * while receiving a floating rate indexed to a reference rate (such as LIBOR or EURIBOR). By market convention, the counterparty 
	 * paying the fixed rate is called the "payer" (while receiving the floating rate), and the counterparty receiving the fixed rate 
	 * is called the "receiver" (while paying the floating rate).
	 * 
	 * A pays fixed rate to B (A receives floating rate)
	 * B pays floating rate to A (B receives fixed rate)
	 * 
	 * Currently, A borrows from Market @ LIBOR +1.5%. B borrows from Market @ 8.5%.
	 * Consider the following swap in which Party A agrees to pay Party B periodic fixed interest rate payments of 8.65%, in exchange 
	 * for periodic variable interest rate payments of LIBOR + 70 bps (0.70%) in the same currency. Note that there is no exchange of 
	 * the principal amounts and that the interest rates are on a "notional" (i.e. imaginary) principal amount. Also note that the 
	 * interest payments are settled in net (e.g. Party A pays (LIBOR + 1.50%)+8.65% - (LIBOR+0.70%) = 9.45% net). The fixed rate 
	 * (8.65% in this example) is referred to as the swap rate.[2]
	 * 
	 * At the point of initiation of the swap, the swap is priced so that it has a net present value of zero. If one party wants to pay 
	 * 50 bps above the par swap rate, the other party has to pay approximately 50bps over LIBOR to compensate for this.
	 * 
	 */
	
	public interface Rate {
		public FFType getType();
	}
	
	public class FixedRate implements Rate {
		final public FFType rateType;
		final public BigDecimal fixedRate;

		public FixedRate(BigDecimal fixedRate) {
			this.fixedRate = fixedRate;
			this.rateType = FFType.Fixed;
		}
		
		@Override
		public FFType getType() {
			return rateType;
		}

		@Override
		public String toString() {
			return "["+rateType+"Rate="+fixedRate+"%]";
		}
	}

	public enum ReferenceRate {
		LIBOR,
		EURIBOR;
	}
	
	public class FloatingRate implements Rate {
		final public FFType rateType;
		final public BigDecimal floatingRate;
		final ReferenceRate referenceRate;

		public FloatingRate(ReferenceRate referenceRate, BigDecimal floatingRate) {
			this.rateType = FFType.Floating;
			this.floatingRate = floatingRate;
			this.referenceRate = referenceRate;
		}

		@Override
		public FFType getType() {
			return rateType;
		}
		
		@Override
		public String toString() {
			return "[rate="+referenceRate+"+"+floatingRate+"%"+"]";
		}
	}
	
	final public void clearLegs(){
		fixedLeg = null;
		floatingLeg = null;
	}
	
	final public void addLeg(Counterparty counterparty, Rate rate, BigDecimal notional, Currency currency){
		switch( rate.getType()){
		case Fixed:
			if( fixedLeg == null )
				fixedLeg = new Leg(counterparty, rate, notional, currency);
			else if( fixedLeg.rate != rate && fixedLeg.counterparty != counterparty )
				floatingLeg = new Leg(counterparty, rate, notional, currency);
			else if( fixedLeg.rate == rate && fixedLeg.counterparty == counterparty )
				throw new WrongDataModelException("Second transaction leg can not be assign to the same counterparty ["+counterparty+"] with the same rate ["+rate+"]");
			else if( fixedLeg.rate == rate )
				throw new WrongDataModelException("Second transaction leg can not be initialized with the same rate ["+rate+"]");
			else if( fixedLeg.counterparty == counterparty )
				throw new WrongDataModelException("Second transaction leg can not be assign to the same counterparty ["+counterparty+"]");
			break;
		case Floating:
			if( floatingLeg == null )
				floatingLeg = new Leg(counterparty, rate, notional, currency);
			else if( floatingLeg.rate != rate && floatingLeg.counterparty != counterparty )
				fixedLeg = new Leg(counterparty, rate, notional, currency);
			else if( floatingLeg.rate == rate && floatingLeg.counterparty == counterparty )
				throw new WrongDataModelException("Second transaction leg can not be assign to the same counterparty ["+counterparty+"] with the same rate ["+rate+"]");
			else if( floatingLeg.rate == rate )
				throw new WrongDataModelException("Second transaction leg can not be initialized with the same rate ["+rate+"]");
			else if( floatingLeg.counterparty == counterparty )
				throw new WrongDataModelException("Second transaction leg can not be assign to the same counterparty ["+counterparty+"]");
			break;
		default:
			throw new WrongDataModelException("Unexpected rate type ["+rate.getType()+"] for instrument ["+acronim()+"]");
		}
		
	}
	
	final public Counterparty getPayer() {
		return getPayerLeg().counterparty;
	}
	
	final public Leg getPayerLeg(){
		return fixedLeg;
	}
	
	final public Counterparty getReceiver() {
		return getReceiverLeg().counterparty;
	}
	
	final public Leg getReceiverLeg(){
		return floatingLeg;
	}
	
	final public boolean isVanilla(){
		if( fixedLeg == null || floatingLeg == null )
			throw new WrongDataModelException("Both legs must be initialized [leg="+fixedLeg+"][leg="+floatingLeg+"] for instrument ["+acronim()+"]");
		else if( fixedLeg.rate.getType() == FFType.Fixed && floatingLeg.rate.getType() == FFType.Floating )
			return true;
		else
			return false;
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Timing time = new Timing();

		IntrestRateSwap irs = new IntrestRateSwap();
		
		logger.debug("Instrument acronim ["+irs.acronim()+"]");
		
		FixedRate fixedRate = irs.new FixedRate(new BigDecimal("8.65"));
		FloatingRate floatingRate = irs.new FloatingRate(ReferenceRate.LIBOR, new BigDecimal("0.7"));
		FixedRate fixedRate2 = irs.new FixedRate(new BigDecimal("7.77"));
		FloatingRate floatingRate2 = irs.new FloatingRate(ReferenceRate.EURIBOR, new BigDecimal("0.75"));

		Counterparty counterpartyA = irs.new Counterparty("A");
		Counterparty counterpartyB = irs.new Counterparty("B");
		
		irs.addLeg(counterpartyA, fixedRate, new BigDecimal(1000000), Currency.USD);
		irs.addLeg(counterpartyB, floatingRate, new BigDecimal(1000000), Currency.CAD);
		
		logger.debug((irs.isVanilla()?"Vanilla case: ": "Exotic case: ")+irs.getPayerLeg()+" swaps "+irs.getReceiverLeg());

		irs.clearLegs();
		irs.addLeg(counterpartyA, floatingRate2, new BigDecimal(1000000), Currency.USD);
		irs.addLeg(counterpartyB, floatingRate, new BigDecimal(1000000), Currency.CAD);

		logger.debug((irs.isVanilla()?"Vanilla case: ": "Exotic case: ")+irs.getPayerLeg()+" swaps "+irs.getReceiverLeg());

		irs.clearLegs();
		irs.addLeg(counterpartyA, fixedRate, new BigDecimal(1000000), Currency.USD);
		irs.addLeg(counterpartyB, fixedRate2, new BigDecimal(1000000), Currency.CAD);

		logger.debug((irs.isVanilla()?"Vanilla case: ": "Exotic case: ")+irs.getPayerLeg()+" swaps "+irs.getReceiverLeg());

		try {
			irs.clearLegs();
			irs.addLeg(counterpartyA, fixedRate, new BigDecimal(1000000), Currency.USD);
			irs.addLeg(counterpartyA, fixedRate, new BigDecimal(1000000), Currency.CAD);

			logger.debug((irs.isVanilla()?"Vanilla case: ": "Exotic case: ")+irs.getPayerLeg()+" swaps "+irs.getReceiverLeg());
		}catch(Throwable t){
			logger.error("", t);
		}

		try {
			irs.clearLegs();
			irs.addLeg(counterpartyA, fixedRate, new BigDecimal(1000000), Currency.USD);
			irs.addLeg(counterpartyB, fixedRate, new BigDecimal(1000000), Currency.CAD);

			logger.debug((irs.isVanilla()?"Vanilla case: ": "Exotic case: ")+irs.getPayerLeg()+" swaps "+irs.getReceiverLeg());
		}catch(Throwable t){
			logger.error("", t);
		}

		try {
			irs.clearLegs();
			irs.addLeg(counterpartyB, fixedRate, new BigDecimal(1000000), Currency.USD);
			irs.addLeg(counterpartyB, fixedRate, new BigDecimal(1000000), Currency.CAD);

			logger.debug((irs.isVanilla()?"Vanilla case: ": "Exotic case: ")+irs.getPayerLeg()+" swaps "+irs.getReceiverLeg());
		}catch(Throwable t){
			logger.error("", t);
		}

		logger.info(module+" done [time=" + time.total()+ "]");

	}

}
