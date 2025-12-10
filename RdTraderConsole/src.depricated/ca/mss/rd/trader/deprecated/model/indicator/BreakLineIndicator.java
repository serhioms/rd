package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.util.MyColor;
import ca.mss.rd.trader.view.tabs.lab.LabController;

public class BreakLineIndicator extends DefaultIndicator {

	final static public String module = BreakLineIndicator.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(BreakLineIndicator.class);

	private double bli, prebli, preval;
	protected boolean isUp, isDn;
	
	protected double pipe;
	private String color;
	
	private DrawPoint[] drawPoint;
	private final int index;
	
	private String[] traceName;
	private String[] chartName;

	private boolean isValueInitial; //, isValueChanged;
	
	public BreakLineIndicator(LabController ctrl) {
		this(ctrl, 0, 1.0, "red");
	}

	public BreakLineIndicator(LabController ctrl, double pipe) {
		this(ctrl, 0, pipe, "red");
	}

	public BreakLineIndicator(LabController ctrl, int index, double pipe, String color) {
		super(ctrl);
		this.pipe = pipe;
		this.color = color;
		this.index = index;
	}

	@Override
	public boolean isSlow() {
		return false;
	}

	@Override
	public void fire(IndicatorContext ic) {
		drawPoint[0].val = bli;
		drawPoint[0].time = ic.time;
		drawPoint[0].time24 = ic.time24;

		ic.pcs.firePropertyChange(LabController.EVENT_CHART_DRAW, "", drawPoint);
	}

	@Override
	public void init() {
		String key = module+getQuoteName()+pipe;
		
		chartName = getCharts().get(key);
		if (chartName == null) {
			getCharts().put(key, chartName = new String[]{getQuoteName()});
		}
		
		if( (traceName=getTraces().get(key)) == null) {
			getTraces().put(key, traceName = new String[]{getQuoteName()+"-BL "+pipe});
		}
		
		if( (drawPoint = getDrawPoints().get(key)) == null) {
			getDrawPoints().put(key, drawPoint = new DrawPoint[] { 
					 new DrawPoint(getQuoteName(), chartName[0], traceName[0], MyColor.getColor(color))
				});
		}
	}


	@Override
	public String[] getChartName() {
		return chartName;
	}


	@Override
	public String[] getTraceName() {
		return traceName;
	}

	@Override
	public void clean() {
		clean(drawPoint);
		preval = 0.0;
		isValueInitial = true;
	}

	@Override
	public void process(IndicatorContext ic) {

		final double limit = pipe * ic.spread.val; //(ic.ask - ic.bid);

		if (preval == 0.0) {
			preval = bli = prebli = ic.val;
			//ic.isAnyIndValChanged = true; // first value
		}

		final double delta = ic.val - prebli;

		isUp = isDn = false;
		if( Math.abs(delta) < limit ){
			ic.bl[index] = bli = prebli;
			// isValueChanged = false;
		} else {
			ic.bl[index] = bli =  prebli + delta;
			isUp = delta > 0;	
			isDn = delta < 0;
			isValueInitial = false; 
			// isValueChanged = true;
		}

		if( isValueInitial ){
			double amp = ic.valmax - ic.valmin;
			if( amp > limit ){
				if( ic.val > bli ) {
					logger.debug("Move down ["+pipe+"] to ["+ic.valmin+"] on "+ic.time);
					ic.bl[index] = bli = ic.val;
					isUp = true;	
					isDn = false;
					isValueInitial = false; 
				} else {
					logger.debug("Move up ["+pipe+"] to ["+ic.valmax+"] on "+ic.time);
					ic.bl[index] = bli = ic.val;
					isUp = false;	
					isDn = true;
					isValueInitial = false; 
				}
				// isValueChanged = true;
			}
		}
		
		preval = ic.val;
		prebli = bli;
		
		//ic.isAnyIndValChanged |= isValueChanged;
	}

}
