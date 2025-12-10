package ca.mss.rd.trader.deprecated.model.indicator;

import java.util.Map;

import ca.mss.rd.trader.deprecated.collector.BidAskMidQuote;
import ca.mss.rd.trader.model.Point;

import ca.mss.rd.util.UtilMath;

@Deprecated
public class QuoteSlicer extends DefaultIndicator {

	public static final long serialVersionUID = QuoteSlicer.class.hashCode();
	final static public String module = QuoteSlicer.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static private int SIZE = 0;
	final static public int SLICE = SIZE++;
	final static public int PREV_SLICE = SIZE++;

	final static private String[] TITLE = new String[]{"TIC", "TREND"};
	final private Point[] point = new Point[]{new Point(SIZE, PREV_SLICE, SLICE), new Point(SIZE, PREV_SLICE, SLICE)};
	
	private double val, preval;
	private double time;

	private double delta2, delta;
	private double val1, preval1;
	private double val0, preval0;
	private double limit;
	private int counter;
	
	final public double pipe;
	
	public QuoteSlicer(Map<String,String> param) {
		super(param);
		this.pipe = Double.parseDouble(param.get("pipe"));
		for(int i=0; i<point.length; i++){
			point[i].reinitialize();
		}
	}

	@Override
	public void start() {
		val = preval = time = delta2 = delta = val1 = preval1 = val0 = preval0 = limit = 0.0;

		preval = Double.MAX_VALUE;
		limit  = -Double.MAX_VALUE;
		counter = 0;
		
		for(int i=0; i<point.length; i++){
			point[i].reinitialize();
		}
	}

	@Override
	public void process(Point p){
		
		val = p.val[BidAskMidQuote.MID];
		time = p.time;
		
		// Slicer start
		counter++;
		
		if( preval == Double.MAX_VALUE ){
			preval = val;
			preval0 = 0;
			preval1 = val;
		}
		
		delta = val-preval;
		delta2 = val-preval1;
		
		limit = (p.val[BidAskMidQuote.ASK]-p.val[BidAskMidQuote.BID])*pipe;
		
		if( UtilMath.abs(delta2) < limit ){
			val0 = preval0 + delta;
			val1 = preval1;
		} else {
			val0 = 0;
			val1 = preval1 + delta2;
		}
		
		// Slicer end
		point[0].val[PREV_SLICE] = point[0].val[SLICE];
		point[0].val[SLICE] = val0;
		point[0].time = time;
		
		point[1].val[PREV_SLICE] = point[1].val[SLICE];
		point[1].val[SLICE] = val1;
		point[1].time = time;
		
		preval = val;
		preval0 = val0;
		preval1 = val1;
		
		final double error = UtilMath.abs(val0 + val1 - val);
		if( error > 0.0 ){
			System.out.println("Outgoing point [#="+counter+"] do not nmatch incoming [error="+error+"]");
			System.exit(0);
		}
	}
	
	@Override
	public boolean isChange(int i) {
		return point[i].val[PREV_SLICE] == point[i].val[SLICE];
	}

	@Override
	public Point getPoint(int i) {
		return point[i];
	}

	@Override
	public int size() {
		return QuoteSlicer.TITLE.length;
	}

	@Override
	public String getTitle(int i) {
		return QuoteSlicer.TITLE[i];
	}

	@Override
	public int getIndex(String title) {
		for(int i=0; i<QuoteSlicer.TITLE.length; i++)
			if( title.equals(QuoteSlicer.TITLE[i]))
				return i;
		return Integer.parseInt(title); // Expect # here
	}

}
