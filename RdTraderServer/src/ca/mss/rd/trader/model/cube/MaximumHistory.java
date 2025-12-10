package ca.mss.rd.trader.model.cube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mss.rd.trader.model.TradeOrderReport;
import ca.mss.rd.trader.model.cube.RevAvgCubeFinder.MACube2.MAPoint;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilSort;

public class MaximumHistory {

	public final Maximum max, lastMax;
	public double totalPNL;

	private final List<Maximum> sort;
	private final Map<String, Maximum> map, removed;

	public MaximumHistory() {
		sort = new ArrayList<Maximum>();
		map = new HashMap<String, Maximum>();
		removed = new HashMap<String, Maximum>();
		max = new Maximum();
		lastMax = new Maximum();
	}

	private void add(Maximum max) {
		if( max.profit <= 0.0 ){
			return;
		}
		
		String key = max.point.getKey();

		if (!map.containsKey(key)) {
			Maximum clone = max.clone();
			map.put(key, clone);
			sort.add(clone);
		} else {
			map.get(key).profit = max.profit;
		}

		UtilSort.sortSortable(sort);
	}

	public String report() {
		String result = "";

		for (Maximum max : sort) {
			result += String.format(" %5.2f[%s]", max.profit, max.point.getCoordinates());
		}
		
		return result;
	}

	public void check(double totalProfit, MAPoint pnt) {
		max.check(totalProfit, pnt);
		
		// Update profit
		String key = pnt.getKey();
		if (map.containsKey(key)) {
			if( totalProfit <= 0.0 ){
				Maximum max = map.remove(key);
				sort.remove(max);
				removed.put(key, max);
			} else {
				map.get(key).profit = totalProfit;
			}
		}
	}

	public void clear() {
		max.clear();
	}

	public boolean isNoChanges() {
		return lastMax.isEquals(max);
	}

	public String getExtrRegion() {
		if( lastMax.left != null && lastMax.right != null )
			return String.format("(slow(%d-%d/%d):fast(%d-%d/%d))", lastMax.left.getSlow(), lastMax.right.getSlow(), lastMax.step.getSlow(), lastMax.left.getFast(), lastMax.right.getFast(), lastMax.step.getFast());
		else
			return "";
	}

	public boolean isSamePoint() {
		return max.point.isEquals(lastMax.point);
	}

	public boolean isSameProfit() {
		return max.profit == lastMax.profit;
	}

	public void updateProfit() {
		lastMax.setProfit(max.profit);
		add(lastMax);
	}

	public void updatePoint() {
		lastMax.set(max);
		add(lastMax);
	}

	public TradeOrderReport generateCloseSignal(TradeOrderReport order, MAPoint pnt) {
		if( map.containsKey( pnt.getKey()) || removed.containsKey( pnt.getKey()) ){
			if( order.getId() > 0 ){
				totalPNL += order.getPnl();
				Logger.SIGNAL_CLS.printf("%-10s\t%-4s\t%-5s\t%-3s\t%-6s\t%-6s\t%-6s\t%-10s\t%-6s\t%-7s\t%-7s\t%-6s\t%-12s\t%-8s\t%s", 
						UtilDateTime.now(order.openTime), 
						order.getOpenType(), 1.0, order.symbol, order.openPrice, "", "", 
						UtilDateTime.now(order.closeTime), order.closePrice, 
						String.format("{%-6.2f", order.getPnlMin()), 
						String.format("%-6.2f}", order.getPnlMax()), 
						String.format("%-6.2f", order.getPnl()), pnt.getCoordinates(), 
						String.format("%-8.2f", totalPNL), 
						String.format("Order ID_%d", order.getId()));
			}
		}
		return order;
	}

	int tradeId = 1;
	public TradeOrderReport generateOpenSignal(TradeOrderReport order, MAPoint pnt) {  
		if( map.containsKey( pnt.getKey()) ){
			order.setId(tradeId++);
			Logger.SIGNAL_OPN.printf("%-10s\t%-4s\t%-5s\t%-3s\t%-6s\t%-6s\t%-6s\t%-10s\t%-6s\t%-7s\t%-7s\t%-6s\t%-12s\t%-8s\t%s", 
					UtilDateTime.now(order.openTime), 
					order.getOpenType(), 1.0, order.symbol, order.openPrice, "", "", 
					"", "", 
					"", 
					"", 
					"", pnt.getCoordinates(), 
					"", 
					String.format("Order ID_%d", order.getId()));
		}
		return order;
	}
	
	public String reportHeader = String.format("%-10s\t%-4s\t%-5s\t%-3s\t%-6s\t%-6s\t%-6s\t%-10s\t%-6s\t%-6s\t%-6s\t%-6s\t%-6s\t%-6s\t%-8s\t%-8s", "OpTime","Type","Volume","Symbol","Price","S/L","T/P","ClTime","Price","ProMin","ProMax","Profit","Slow","Fast","Total","Comment");
	
}
