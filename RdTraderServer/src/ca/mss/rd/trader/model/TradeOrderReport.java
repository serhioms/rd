package ca.mss.rd.trader.model;

import ca.mss.rd.util.UtilDateTime;

public class TradeOrderReport extends TradeOrder {

	private static final String REPORT_FORMAT = "%-10s\t%-4s\t%-5s\t%-3s\t%-6s\t%-10s\t%-6s\t%-7s\t%-7s\t%-6s\t%-8s\t%s";
	
	final private TradeModel<TradeOrderReport> tradeModel;

	public TradeOrderReport(TradeModel<TradeOrderReport> tradeModel,  double avgSpread) {
		super(avgSpread);
		this.tradeModel = tradeModel;
	}

	public String reportHeader(){
		return tradeModel.reportHeader = String.format(REPORT_FORMAT, 
				"Open",
				"Type","Volume","Symbol","Price",
				"Close","Price",
				" min","max",
				"Profit",
				"Total",
				"Comment");
	}
	
	public String reportBody(boolean isOpen, String reason){
		
		double profit = getPnl();
		
		if( !isOpen ){
			tradeModel.addTotalProfit(profit);
		}
		
		double promin = isOpen? 0.0: getPnlMin();
		double promax = isOpen? 0.0: getPnlMax();

		return tradeModel.reportBody = String.format(REPORT_FORMAT, 
				UtilDateTime.now(openTime), 
				getOpenType(), 1.0, symbol, openPrice,
				isOpen?"":UtilDateTime.now(closeTime), isOpen?"":closePrice, 
				isOpen?"":String.format("%-6.2f", promin), 
				isOpen?"":String.format("%-6.2f", promax), 
				String.format("%-6.2f", profit),
				isOpen?"":String.format("%-8.2f", tradeModel.getTotalProfit()), 
				reason);
	}
	
	public String reportBody(boolean isOpen, String comment, int slow, int fast){
		return reportBody(isOpen, String.format("%-6d\t%-6d\t%s", slow, fast, comment));
	}
}
