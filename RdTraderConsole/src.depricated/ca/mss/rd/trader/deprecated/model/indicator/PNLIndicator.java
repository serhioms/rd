package ca.mss.rd.trader.model.indicator;

import java.math.BigDecimal;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trade.forex.FX;
import ca.mss.rd.trade.forex.FX.Currency;
import ca.mss.rd.trader.util.MyColor;
import ca.mss.rd.trader.view.tabs.lab.LabController;

public class PNLIndicator extends DefaultIndicator {

	final static public String module = PNLIndicator.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static private final BigDecimal LEVERAGE = new BigDecimal("10000", FX.LEVERAGE);
	static private final BigDecimal UNITS = new BigDecimal("10000", FX.UNITS);
	static private final BigDecimal QH_RATE = new BigDecimal("10000.0", FX.PRICE);
	
	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;

	final private FX bsbFx, sbbFx;
	public boolean isBSB, isSBB;

	private BigDecimal qhExrate;

	protected BigDecimal bsb;
	protected BigDecimal sbb;
	protected BigDecimal summa;
	protected BigDecimal total;

	public PNLIndicator(LabController ctrl) {
		super(ctrl);
		bsbFx = new FX(Currency.CAD, LEVERAGE);
		sbbFx = new FX(Currency.CAD, LEVERAGE);
		isBSB = true;
		isSBB = true;
		qhExrate = QH_RATE;
	}

	@Override
	public boolean isSlow() {
		return true;
	}

	@Override
	public void fire(IndicatorContext ic) {
		drawPoint[0].val = bsb.doubleValue();
		drawPoint[0].time = ic.time;
		drawPoint[0].time24 = ic.time24;

		drawPoint[1].val = sbb.doubleValue();
		drawPoint[1].time = ic.time;
		drawPoint[1].time24 = ic.time24;

		drawPoint[2].val = summa.doubleValue();
		drawPoint[2].time = ic.time;
		drawPoint[2].time24 = ic.time24;

		drawPoint[3].val = total.doubleValue();
		drawPoint[3].time = ic.time;
		drawPoint[3].time24 = ic.time24;

		ic.pcs.firePropertyChange(LabController.EVENT_CHART_DRAW, "", drawPoint);
	}

	@Override
	public void init() {
		String key = module + getQuoteName();

		chartName = getCharts().get(key);
		if (chartName == null) {
			getCharts().put(key, chartName = new String[] { getQuoteName() + "_PNL" });
		}

		if ((traceName = getTraces().get(key)) == null) {
			getTraces()
					.put(key,
							traceName = new String[] { getQuoteName() + "-BSB", getQuoteName() + "-SBB", getQuoteName() + "-SMM" , getQuoteName() + "-TTL" });
		}

		if ((drawPoint = getDrawPoints().get(key)) == null) {
			getDrawPoints().put(
					key,
					drawPoint = new DrawPoint[] {
							new DrawPoint(getQuoteName(), chartName[0], traceName[0], MyColor.getColor("green")),
							new DrawPoint(getQuoteName(), chartName[0], traceName[1], MyColor.getColor("blue")),
							new DrawPoint(getQuoteName(), chartName[0], traceName[2], MyColor.getColor("white")),
							new DrawPoint(getQuoteName(), chartName[0], traceName[3], MyColor.getColor("red")) });
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
		total = new BigDecimal(0.0);
		bsb = new BigDecimal(0.0);
		sbb = new BigDecimal(0.0);
		summa = new BigDecimal(0.0);
	}

	@Override
	public void process(IndicatorContext ic) {
		calculate(ic);
	}

	/*
	 * Implementation
	 */
	public void doBuy(IndicatorContext ic) {
		bsbFx.doBuy(UNITS, new BigDecimal(ic.ask, FX.PRICE));
		isBSB = true;
	}

	public void sellBack() {
		if( isBSB ){
			isBSB = false;
			total = total.add(bsb);
			bsb = new BigDecimal(0.0);
		}
	}

	public void doSell(IndicatorContext ic) {
		sbbFx.doSell(UNITS, new BigDecimal(ic.bid, FX.PRICE));
		isSBB = true;
	}

	public void buyBack() {
		if( isSBB ){
			isSBB = false;
			total = total.add(sbb);
			sbb = new BigDecimal(0.0);
		}
	}

	protected void calculate(IndicatorContext ic) {
		summa = total;
		if (isBSB) {
			if (bsbFx.isNotBought()) {
				doBuy(ic);
			}
			bsb = bsbFx.sellBack(new BigDecimal(ic.bid, FX.PRICE), qhExrate).getPNL();
			summa = summa.add(bsb);
		}
		if (isSBB) {
			if (sbbFx.isNotSold()) {
				doSell(ic);
			}
			sbb = sbbFx.buyBack(new BigDecimal(ic.ask, FX.PRICE), qhExrate).getPNL();
			summa = summa.add(sbb);
		}
	}

}
