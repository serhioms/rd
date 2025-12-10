package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.view.tabs.lab.LabController;

@Deprecated
public class BLUpDownIndicator extends BreakLineIndicator {

	final static public String module = BLUpDownIndicator.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public String DEFAULT_COLOR_UP = "red";
	static public String DEFAULT_COLOR_DOWN = "green";

	private String colorUp, colorDn;
	private DrawPoint[] drawPoint;

	private String[] traceName;
	private String[] chartName;
	
	protected boolean lastUp, lastDn;
	
	public BLUpDownIndicator(LabController ctrl) {
		super(ctrl);
		this.colorUp = DEFAULT_COLOR_UP;
		this.colorDn = DEFAULT_COLOR_DOWN;
	}

	public BLUpDownIndicator(LabController ctrl, double pipe) {
		super(ctrl, pipe);
		this.colorUp = DEFAULT_COLOR_UP;
		this.colorDn = DEFAULT_COLOR_DOWN;
	}

	public BLUpDownIndicator(LabController ctrl, int index, double pipe, String color) {
		super(ctrl, index, pipe, color);
		this.colorUp = DEFAULT_COLOR_UP;
		this.colorDn = DEFAULT_COLOR_DOWN;
	}


	@Override
	public void fire(IndicatorContext ic) {
		super.fire(ic);

//		drawPoint[0].y[0] = 0;
//		drawPoint[0].t = ic.time;
//
//		ic.pcs.firePropertyChange(LabController.EVENT_CHART_DRAW, null, drawPoint);
		
	}

	@Override
	public void init() {
		super.init();

		String key = module+getQuoteName();
		
		chartName = getCharts().get(key);
		if (chartName == null) {
			getCharts().put(key, chartName = merge(super.getChartName(), new String[]{/*getQuoteName()+"-UpDn"*/}));
		}
		
		if( (traceName=getTraces().get(key)) == null) {
			getTraces().put(key, traceName = merge(super.getTraceName(), new String[]{/*getQuoteName()+"-UP", getQuoteName()+"-DOWN"*/}));
		}
		
		if( (drawPoint = getDrawPoints().get(key)) == null) {
						  getDrawPoints().put(key, drawPoint = new DrawPoint[] {/* 
					  new DrawPoint(2, getQuoteName(), chartName[0], traceName[0], MyColor.getColor(colorUp))
					 ,new DrawPoint(2, getQuoteName(), chartName[0], traceName[1], MyColor.getColor(colorDn))*/
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
		super.clean();
		clean(drawPoint);
		lastDn = lastUp = false;
	}

	@Override
	public void process(IndicatorContext ic) {
		super.process(ic);
		
//		if( (super.isUp && lastDn) ){
//			logger.debug(String.format("%2.3f\t%s\t%4d\t%.0f", super.pipe, "MIN", ic.time, ic.val()));
//		} else if( super.isDn && lastUp ){
//			logger.debug(String.format("%.3f\t%s\t%4d\t%.0f", super.pipe, "MAX", ic.time, ic.val()));
//		}
//		
//		if( super.isUp ){
//			logger.debug(String.format("%2.3f\tUP\t%4d\t%.0f", super.pipe, ic.time, ic.val()));
//		}
//		if( super.isDn ){
//			logger.debug(String.format("%.3f\tDN\t%4d\t%.0f", super.pipe, ic.time, ic.val()));
//		}
		
		if( super.isUp ){
			lastUp = true;
			lastDn = false;
		} else if( super.isDn ){
			lastDn = true;
			lastUp = false;
		}
	}

}
