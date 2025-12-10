package ca.mss.rd.trade.derivatives.fut;

import java.math.BigDecimal;
import java.util.Date;

import ca.mss.rd.trade.parser.optexzerocurve.QuantifiOptexZeroCurveParser;
import ca.mss.rd.trade.parser.optexzerocurve.QuantifiOptexZeroCurveParserRow;


public class ZeroCurve {

	final static public String module = ZeroCurve.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final public String tenor;
	final public String curveName;
	final public Date curveDate;
	final public String marketSet;
	final public String type;
	final public String label;
	final public Date maturityDate;
	final public BigDecimal calculatedYield;
	final public int sortOrder;
	
	
	public ZeroCurve(String tenor, String curveName, Date curveDate, String marketSet, String type, String label, Date maturityDate, BigDecimal calculatedYield, int sortOrder) {
		this.tenor = tenor;
		this.curveName = curveName;
		this.curveDate = curveDate;
		this.marketSet = marketSet;
		this.type = type;
		this.label = label;
		this.maturityDate = maturityDate;
		this.calculatedYield = calculatedYield;
		this.sortOrder = sortOrder;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		QuantifiOptexZeroCurveParser<QuantifiOptexZeroCurveParserRow> parser = new QuantifiOptexZeroCurveParser<QuantifiOptexZeroCurveParserRow>("data\\optex\\quantifi\\20121217\\Next-Data\\OptexCurves.20121217.csv");
		if (parser.isValid()) {
			
			for(parser.readHeader(); parser.hasNext(); ){
				QuantifiOptexZeroCurveParserRow.Record record = parser.next().record;
				
				if( !"fut".equals(record.getType().trim()) )
					continue;
					
				logger.debug(record.getTenor()+"\t"+record.getCurvename()+"\t"+record.getCurvedate()+"\t"+record.getMarketset()
						+"\t"+record.getType()+"\t"+record.getLabel()+"\t"+record.getMaturityDate()+"\t"+record.getCalculatedYield()
						+"\t"+record.getSortorder());
			}
		}
		
	}

}
