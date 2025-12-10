package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.stat.BreakLine;
import ca.mss.rd.trader.util.MyColor;
import ca.mss.rd.trader.view.tabs.lab.LabController;

public class BLIndicator extends DefaultIndicator {

	final static public String module = BLIndicator.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(BLIndicator.class);

	private BreakLine bl;
	
	protected double pipe;
	private String color;
	private final int index;
	
	private DrawPoint[] drawPoint;
	
	private String[] traceName;
	private String[] chartName;

	public BLIndicator(LabController ctrl) {
		this(ctrl, 0, 1.0, "orange");
	}

	public BLIndicator(LabController ctrl, double pipe) {
		this(ctrl, 0, pipe, "orange");
	}

	public BLIndicator(LabController ctrl, int index, double pipe, String color) {
		super(ctrl);
		this.pipe = pipe;
		this.color = color;
		this.index = index;
		this.bl = new BreakLine();
	}

	@Override
	public boolean isSlow() {
		return false;
	}

	@Override
	public void fire(IndicatorContext ic) {
		drawPoint[0].val = bl.getBLValue();
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
			getTraces().put(key, traceName = new String[]{getQuoteName()+"-BLine "+pipe/*, getQuoteName()+"-MAX "+pipe, getQuoteName()+"-MIN "+pipe*/});
		}
		
		if( (drawPoint = getDrawPoints().get(key)) == null) {
			getDrawPoints().put(key, drawPoint = new DrawPoint[] { 
					 new DrawPoint(getQuoteName(), chartName[0], traceName[0], MyColor.getColor(color))
//					 ,new DrawPoint(getQuoteName(), chartName[0], traceName[1], MyColor.getColor("red"))
//					 ,new DrawPoint(getQuoteName(), chartName[0], traceName[2], MyColor.getColor("green"))
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
		bl.clear();
	}

	@Override
	public void process(IndicatorContext ic) {

		final double limit = pipe * (ic.ask - ic.bid);

		bl.addValue(ic.val, limit);
		ic.bl[index] = bl.getBLValue();
	}

}
