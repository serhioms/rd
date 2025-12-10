package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.util.MyColor;
import ca.mss.rd.trader.view.tabs.lab.LabController;

public class TrendPNLIndicator extends PNLIndicator {

	final static public String module = TrendPNLIndicator.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;

	public boolean isTrendUp, isTrendDw;

	public TrendPNLIndicator(LabController ctrl) {
		super(ctrl);
	}

	@Override
	public void clean() {
		super.clean();
	}

	@Override
	public void process(IndicatorContext ic) {
		super.process(ic);
		generateTradeSignals(ic);
	}

	public void generateTradeSignals(IndicatorContext ic) {
		double bsbd = bsb.doubleValue();
		double sbbd = sbb.doubleValue();
		
		if( bsbd == sbbd ){
			isTrendUp = isTrendDw = false;
		} else {
			isTrendUp = bsbd > sbbd;
			isTrendDw = bsbd < sbbd;
		}
	}
	
	@Override
	public void fire(IndicatorContext ic) {
		super.fire(ic);
		
		drawPoint[0].val = isTrendUp? 10000: (isTrendDw? -10000: 0);
		drawPoint[0].time = ic.time;
		drawPoint[0].time24 = ic.time24;

		ic.pcs.firePropertyChange(LabController.EVENT_CHART_DRAW, "", drawPoint);
	}

	@Override
	public void init() {
		super.init();

		String key = module + getQuoteName();

		chartName = getCharts().get(key);
		if (chartName == null) {
			getCharts().put(key, chartName = merge(super.getChartName(), new String[] { getQuoteName() + "_TREND" }));
		}

		if ((traceName = getTraces().get(key)) == null) {
			getTraces()
					.put(key,
							traceName = merge(super.getTraceName(), new String[] { getQuoteName() + "-TREND"}));
		}

		if ((drawPoint = getDrawPoints().get(key)) == null) {
			getDrawPoints().put(
					key,
					drawPoint = new DrawPoint[] {
							new DrawPoint(getQuoteName(), chartName[0], traceName[0], MyColor.getColor("yellow")) });
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

}
