package ca.mss.rd.trader.model;

import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.stat.BreakLine;
import ca.mss.rd.stat.Delta;
import ca.mss.rd.stat.StdDevMoovingPopulation;
import ca.mss.rd.stat.StdDevPopulationSimple;

public class FilteredMovingAvgModel {

	public final int depthMvng, depthFltr;
	public final double chanelUp, chanelDn; 
	public final double pipe; 
	
	public final StdDevMoovingPopulation md_mvng;
	public final AverageMooving ma_fltr;
	public final BreakLine bl_fltr;

	public final Delta delta = new Delta();
	public final StdDevPopulationSimple md_delta;
	
	public double cntrAll, cntrChanel;
	
	public double val;

	public FilteredMovingAvgModel(int depthMvng, int depthFltr, double chanelUp, double chanelDn, double pipe) {
		this.pipe = pipe;
		this.chanelUp = chanelUp;
		this.chanelDn = chanelDn;
		this.depthMvng = depthMvng;
		this.depthFltr = depthFltr; 
		md_mvng = new StdDevMoovingPopulation(depthMvng); 
		ma_fltr = new AverageMooving(depthFltr);
		md_delta = new StdDevPopulationSimple();
		bl_fltr = new BreakLine();
		bl_fltr.pipe = pipe;
	}

	public void clear(){
		md_mvng.clear();
		md_mvng.setWindowSize(depthMvng);
		
		ma_fltr.clear();
		ma_fltr.setWindowSize(depthFltr);
		
		bl_fltr.clear();
		
		delta.clear();
		md_delta.clear();
		
		cntrAll = cntrChanel = 0.0;
	}
	
	final public void calc(double val) {
		this.val = val;
		
		delta.addValue(val);
		md_mvng.addValue(val);

		cntrAll += 1.0;
		
		if( ma_fltr.isFull() ){
			if( ma_fltr.below(md_mvng.getAverage(), md_delta.getStDev(), chanelUp) ){
				ma_fltr.addValue(md_mvng.getAverage());
				md_delta.addValue(delta.getValue());
				cntrChanel += 1.0;
			}
		} else {
			ma_fltr.addValue(md_mvng.getAverage());
			md_delta.addValue(delta.getValue());
		}

		bl_fltr.addValue(ma_fltr.getAverage(), bl_fltr.pipe * md_delta.getStDev());
	}

	final public double getRight(){
		return ma_fltr.getRight(bl_fltr.getBLValue(), md_delta.getStDev(), chanelUp);
	}
	
	final public double getLeft(){
		//return ma_fltr.getLeft(md_delta.getStDev(), SPREAD_CHANEL_DN);
		return ma_fltr.getLeft(bl_fltr.getBLValue(), md_delta.getStDev(), chanelDn);
	}

	final public boolean isHuge() {
		return val > getRight();
	}

	final public boolean isTiny() {
		return val <= getLeft();
	}
}
