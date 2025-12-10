package ca.mss.rd.trader.model.indicator;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.trader.model.MAPoint;
import ca.mss.rd.trader.model.MARegion;
import ca.mss.rd.trader.model.RevAvgModel;

public class ProfitSurface {
	
	private double[][] table;
	private Map<Integer,Integer> slowMap, fastMap;
	public AverageMooving slow[], fast[];

	public MAPoint spot, point, extr;
	public MARegion region;
	
	public ProfitSurface() {
		point = new MAPoint(0.0);
		extr = new MAPoint(0.0);
	}

	public ProfitSurface(MAPoint spot) {
		this();
		
		this.spot = spot;
		
		this.slow = new AverageMooving[1];
		this.fast = new AverageMooving[1];
		
		this.slow[0] = new AverageMooving(spot.slow);
		this.fast[0] = new AverageMooving(spot.fast);
	}

	public ProfitSurface(MAPoint spot, MARegion region) {
		this();

		this.spot = spot;
		this.region = region;
		
		this.slow = new AverageMooving[region.size.slow];
		this.fast = new AverageMooving[region.size.fast];

		this.slowMap = new HashMap<Integer,Integer>();
		this.fastMap = new HashMap<Integer,Integer>();

		for(int cnt=0, wsize=region.left.slow; cnt < slow.length; wsize+=region.step.slow, cnt++){
			slow[cnt] = new AverageMooving(wsize);
			slowMap.put(slow[cnt].getWindowSize(), cnt);
		}
		
		for(int cnt=0,wsize=region.left.fast; cnt < fast.length; wsize+=region.step.fast, cnt++){
			fast[cnt] = new AverageMooving(wsize);
			fastMap.put(fast[cnt].getWindowSize(), cnt);
		}
		
		this.table = new double[slow.length][fast.length];
	}

	public void addPoint(int slow, int fast, double profit) {
		point.set(slow, fast, profit);
		if( table != null ){
			Integer s = slowMap.get(slow), f=fastMap.get(fast);
			if( s != null && f != null ){
				table[s][f] = profit;
			}
		}
	}
	
	public void clean() {
		extr.set(0, 0, 0.0);
		point.set(0, 0, 0.0);
		for(AverageMooving am: slow ){
			am.clear();
		}
		for(AverageMooving am: fast ){
			am.clear();
		}
	}

	public String getTable() {
		String s = "There is no surface exists!"; 
		if( table != null ){
			s += String.format("Surface for (%d,%d) step (%d,%d)\n", spot.slow, spot.fast, region.step.slow, region.step.fast);
			s += "      ";
			for(int j=0; j<fast.length; j++){
				s += String.format("\t%-6d", fast[j].getWindowSize()); 
			}
			s += "\n"; 
			for(int i=0; i<slow.length; i++){
				s += String.format("%-6d", slow[i].getWindowSize()); 
				for(int j=0; j<fast.length; j++){
					s += String.format("\t%-6.2f", table[i][j]); 
				}
				s += "\n"; 
			}
		}
		return s;
	}

	public void process(double mid, RevAvgModel ramodel) {
		boolean isFull = true;
		
		for(AverageMooving am: slow ){
			am.addValue(mid);
			isFull &= am.isFull();
		}

		for(AverageMooving am: fast ){
			am.addValue(mid);
			isFull &= am.isFull();
		}
		
		if( isFull ){
			for(int j=0; j<slow.length; j++)
				for(int i=0; i<fast.length; i++){
					
					if( fast[i].getWindowSize() > slow[j].getWindowSize() ){
						break;
					}

					// Find maximums
					ramodel.process(slow[j], fast[i], this);
					if( extr.checkMax(point) ){
						System.out.println("xexe 1");
					}
				}
		}
		
	}
}

