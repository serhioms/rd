package ca.mss.rd.trader.deprecated;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.trader.deprecated.beans.TraceMap;

@Deprecated
public class ChartModel {

	final static public Color COLOR_MAX = Color.RED;
	final static public Color COLOR_MIN = Color.GREEN;
	final static public Color COLOR_ASK = COLOR_MAX;
	final static public Color COLOR_BID = COLOR_MIN;
	final static public Color COLOR_BSB = COLOR_MAX;
	final static public Color COLOR_SBB = COLOR_MIN;
	final static public Color COLOR_MID = Color.BLUE;
	final static public Color COLOR_LOG = Color.MAGENTA;
	final static public Color COLOR_MIDN = Color.MAGENTA;
	final static public Color COLOR_PNL = Color.WHITE;
	final static public Color COLOR_TOTAL = Color.YELLOW;

	static private List<TraceMap> traces = new ArrayList<TraceMap>();
			
	static public boolean isTraceShown(String ident){
		for(int i=0,size=traces.size(); i<size; i++)
			if( traces.get(i).containsKey(ident) )
				return true;
		return false;
	}
	
	static {
		TraceMap trace = new TraceMap();
//		trace.put("ASK", COLOR_ASK);
//		trace.put("BID", COLOR_BID);
//		trace.put("LOG", COLOR_LOG);
//		trace.put("MID", COLOR_MID);

		trace.put("BidAskMidSpread", Color.BLUE);

//		for(int i=0; i< BidAskDataCollector.POOL_SIZE; i++){
//			int ALL = 1;
//			if( i == ALL ){
//				trace.put("Mn"+i, COLOR_MIN);
//				trace.put("Mx"+i, COLOR_MAX);
//				trace.put("Md"+i, COLOR_MIDN);
//			}
//		}

//		trace.put("midN", COLOR_MIN);
//		trace.put("midX", COLOR_MAX);
		traces.add(trace);

		
		trace = new TraceMap();
//		trace.put("BSB", COLOR_BSB);
//		trace.put("SBB", COLOR_SBB);
//		trace.put("TOTAL", COLOR_TOTAL);
		trace.put("PNL", COLOR_PNL);

//		trace.put("PnlDfA0", Color.RED);
//		trace.put("PnlDfA1", Color.BLUE);
//		trace.put("PnlDfA2", Color.GREEN);
//		trace.put("PnlDfB1", Color.BLUE);
//		trace.put("PnlDfG2", Color.GREEN);
		traces.add(trace);
	}
	

}
