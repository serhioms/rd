package ca.mss.rd.trader.deprecated.beans;

import ca.mss.rd.table.RdConstant;
import ca.mss.rd.table.RdDBO;
import ca.mss.rd.table.RdTable;

public class Scheduler {

	final static public RdDBO<Scheduler> dbo = new RdDBO<Scheduler>(new RdTable<Scheduler>());

	public final WeekDay[] weekday;
	public final boolean[] isDayOff;
	public final int[] frH24;
	public final int[] toH24;
	
	
	private Scheduler(int[][] table) {
		super();
		
		this.weekday = new WeekDay[table.length];
		this.isDayOff = new boolean[table.length];
		this.frH24 = new int[table.length];
		this.toH24 = new int[table.length];
		
		for(int i=0; i<table.length; i++){
			if( table[i] == null ){
				this.weekday[i] = WeekDay.values()[i];
				this.isDayOff[i] = true;
				this.frH24[i] = RdConstant.NA_H24;
				this.toH24[i] = RdConstant.NA_H24;
			} else {
				this.weekday[i] = WeekDay.values()[i];
				this.isDayOff[i] = false;
				this.frH24[i] = table[i][0];
				this.toH24[i] = table[i][1];
			}
		}
	}


	public enum WeekDay {
		Sn,
		Mn,
		Tu,
		Wd,
		Th,
		Fr,
		St;
	}

	static {
		dbo.table.add(Floor.FloorEnum.Oanda.toString(), new Scheduler(
				new int[][]{
					null, 
					new int[]{8,17},
					new int[]{8,17},
					new int[]{8,17},
					new int[]{8,17},
					new int[]{8,17},
					null
				}
		));
	}
}
