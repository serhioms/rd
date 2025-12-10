/**
 * 
 */
package ca.mss.rd.trade.calypso.environment;

import ca.mss.rd.trade.value.Value;
import ca.mss.rd.trade.value.ValueBoolean;

/**
 * @author moskovsk
 *
 */
public enum PropertyDefault implements Property {

	ADVICE_ON_SETTLEDATE(new ValueBoolean(true)), /* 
	
	ADVICE_ON_SETTLEDATE determines when the system generates payment advices.   
	
	Y (default value)

	Set this property to Y so the Message Engine creates advices on the settle date.
		
		To create advices N days before the settle date, modify the message workflow.
		 
		Choose Main Entry > Configuration > Workflow > Workflow Configuration to open the WorkFlow Config window.
		» Select the message workflow for payment advice. 
		» Modify the PENDING-AUTHORIZE-VERIFIED transition to include the CheckKickOff rule.  
		» Click Save. 
		
		From the WorkFlow Config window, choose Domains > KickOffCutOff Config to open the KickOffCutOff Config 
		Window. 
		
		Setup the KickOff to –N days before the settle date. 
		In the example below, we set it to -2 days, so the system sends the advice two days before the settle 
		date. 

	Set this property to N to create advices as soon as the Message Engine receives events.	*/
	
	
	CASH_SETTLEMENT_TRADE(new ValueBoolean(false)), /*
	 
	CASH_SETTLEMENT_TRADE allows you to view at the trade level if the trade has cash settlement information (that 
	is, information relating to early terminations or break clauses) attached. Otherwise, you need to check each trade 
	individually by opening its Cash Settlement window; choose ProductName > Cash Settle Info to open this window. 
	
	If you set this property to Y, when you attach cash settlement information to the trade and click Save, the 
	application displays Cash Settled Trade in the status bar at the bottom of the trade worksheet. If you do not have 
	the status bar already displayed, the application automatically opens it. 
	
	[NOTE: Setting this property to Y may affect loading time and performance] 
	N (default value) 
	
	If you set this property to N, you need to open the Cash Settlement window to determine if the trade has cash 
	settlement information attached. */
	
	DIFFERENT_RESET_DT_PER_CPN(new ValueBoolean(true)), /*
		Y or N. Y to generate the reset dates based on the coupon payment frequency, or N to generate the reset dates 
		based on the index tenor. 
		For a coupon frequency higher than the index frequency, all coupons within an index term can have a different 
		reset date, or the same reset date. 
		For example, LIBOR 3M and coupon frequency = M. If Y, every coupon within the index term will have a different 
		reset date. If N, every coupon within the index term will have the same reset date. 
		You can set this property locally at the trade level, or globally at the system level. 
		Trade Level 
		In the trade worksheet, open the Swap Detail window from the floating leg panel. 
		Select a value from the “Different Reset Dates Per Coupon” drop-down menu: 
		• “Blank” (default value) — Use the value from the DIFFERENT_RESET_DT_PER_CPN environment property 
		(see below). 
		• True — The reset dates are based on the coupon payment frequency */
	
	DISALLOW_TRADE_SAVE_WO_PRICE(new ValueBoolean(true)),	/* 
	DISALLOW_TRADE_SAVE_WO_PRICE specifies whether you can save a trade when you have customized the 
	cashflows, changed the trade (for example, the spread), and have not regenerated the cashflows. 
	
	Y If you try to save the trade without regenerating the cashflows, you receive a warning message and cannot save 
	the trade. 
	
	N (default value) 
	You can save a trade without regenerating the cashflows. */
	
	ENABLE_TRADE_NOTES(new ValueBoolean(true)),	/*
	ENABLE_TRADE_NOTES specifies whether you can attach notes to a trade. When you open an existing trade, the 
	note(s) appear in front of the trade. The back office could use this feature to create notes about the SDI. 
	
	Set this property to Y to enable this feature. 

	Creating Trade Notes 
	
	To create trade notes, from the trade worksheet choose Utilities > Create Note to open the Create Note window. 
	» Enter the information as applicable. 
	» Click Save to save the note. 
	» Click Help for details.

	Viewing Trade Notes
	 
	When you open a trade, the trade notes appear in front of the trade worksheet. Trade notes with the highest 
	severity appear first, then the medium severity, and finally the low severity. You cannot access the trade 
	worksheet until you dismiss or close the trade notes. 
	• Dismiss — Click Dismiss if you do not want a note to appear with the trade again. You cannot dismiss a 
	permanent note, and you cannot dismiss a non-permanent note if you do not have permission to dismiss 
	notes. 
	• Close — Click Close to close the note. 
	To view trade notes after you have closed them, choose Utilities > Show Notes.	*/
	
	OPTION_FEE_PRECISION(new Value(2)),	/*
		 
		OPTION_FEE_PRECISION specifies whether to input fees for option products as an amount or percentage. 
		
		The default value is 2, which specifies to enter fees as amounts.
		 
		If you set this property to 5, then you can enter fees as a percentage with five decimal places of precision. */
	
	SCENARIO_OPTIMIZE_BY_PRODUCT(new ValueBoolean(false)), /*
		This environment property is used for CDS Index products for Scenario Optimization.  
		
		Set to Y to enable this feature.  When the optimization is turned on, there are less calls to the Pricer.price()
		method when you run Scenario on CDS Index trades with different characteristics, and the log file is much smaller. 

		The default is N, meaning the optimization is turned off.	*/
	
	TRADE_VERSION_INC(new ValueBoolean(true)), /*
		TRADE_VERSION_INC relates to the audit trail of trades, which you can view in the Trade Audit Viewer window; 
		from trade worksheets, choose Back Office > Audit to open the window. 
		
		Y (default value)
		 
		If you set this property to Y, and open and save a trade without making any changes, the version number 
		increases. However, the audit trail does not display this version because it is a “non-changed” version.*/
	
	
	USE_PARENT_PO(new ValueBoolean(true)); /*
		USE_PARENT_PO specifies that in a parent/child relationship between processing organizations, if the child does 
		not have any workflow setup, then the child can use the parent’s workflow. Note that if the child has a workflow for 
		a specific product only, it cannot use the parent’s workflow for all products, so you can only trade products setup in 
		the child’s workflow. 
		
		Set this property to Y to enable this feature. 	*/
	
	
	public Value value;

	/**
	 * @param value
	 */
	private PropertyDefault(Value value) {
		this.value = value;
	}
	
}


