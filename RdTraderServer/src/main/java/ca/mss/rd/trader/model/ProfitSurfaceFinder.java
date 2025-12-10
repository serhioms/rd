package ca.mss.rd.trader.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mss.rd.trader.model.indicator.ProfitSurface;

public class ProfitSurfaceFinder {

	//private double[][] surface = new double[SLOW_SIZE_MAX][FAST_SIZE_MAX];

	private final List<ProfitSurface> pslist = new ArrayList<ProfitSurface>();
	private final Set<String> isps = new HashSet<String>();
	
	final public MAPoint extrSpot;
	final public MARegion extrReg;

	
	public ProfitSurfaceFinder() {
		extrSpot = new MAPoint(0.0);
		extrReg = new MARegion(0.0);
	}

	public boolean isExist1(){
		return isps.contains(key(extrReg));
	}
	
	public boolean isExistN(){
		return isps.contains(key(extrSpot));
	}
	
	public void add(ProfitSurface profitSurface) {
		pslist.add(profitSurface);
		isps.add(key(profitSurface));
	}

	public String getSurfaceLength() {
		String length = "";
		for(ProfitSurface ps:pslist){
			length += String.format("(%4d,%-4d)", ps.slow.length, ps.fast.length);
		}
		return length;
	}

	public void clean() {
		for(ProfitSurface s: pslist){
			s.clean();
		}
	}

	public void process(double mid, RevAvgModel ramodel) {
		for(ProfitSurface ps: pslist){
			ps.process(mid, ramodel);
			if( extrReg.checkMax(ps.extr) ){
				System.out.println("xexe 2");
			}
		}
	}

	public String getTable() {
		String table = "";
		for(int i=0,size=pslist.size(); i< size; i++){
			ProfitSurface ps = pslist.get(i);
			table += String.format("(%d,%d)\n", ps.extr.slow, ps.extr.fast);
			table += ps.getTable() + "\n\n";
		}
		return table;
	}

	public boolean isAnyExtrStepSmall() {
		return false; //extrReg.step.slow/extrReg.breakSize < extrReg.breakSize/2 || extrReg.step.fast/extrReg.breakSize < extrReg.breakSize/2;
	}
	
	private String key(MAPoint map) {
		return String.format("%d/%d", map.slow, map.fast);
	}

	private String key(MARegion extrReg) {
		return String.format("%d/%d-%d/%d", extrReg.left.slow, extrReg.left.fast, extrReg.step.slow, extrReg.step.fast);
	}

	private String key(ProfitSurface ps) {
		if( ps.region != null )
			return String.format("%d/%d-%d/%d", ps.point.slow, ps.point.fast, ps.region.step.slow, ps.region.step.fast);
		else
			return key(ps.point);
	}

}
