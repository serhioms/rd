package ca.mss.rd.trader.deprecated.beans;

import java.util.List;

import ca.mss.rd.table.RdDBO;
import ca.mss.rd.table.RdTable;

public class Floor {

	final static public RdDBO<Floor> dbo = new RdDBO<Floor>(new RdTable<Floor>());

	final public FloorEnum floorEnum;
	final public Scheduler floorScheduler;
	final public List<CurrencyPair> floorPairs;
	
	public Floor(FloorEnum floorEnum, Scheduler floorScheduler, List<CurrencyPair> floorPairs) {
		super();
		this.floorEnum = floorEnum;
		this.floorScheduler = floorScheduler;
		this.floorPairs = floorPairs;
	}

	public enum FloorEnum {
		Oanda;
	}
	
	static {
		FloorEnum[] floors = FloorEnum.values();
		
		for(int i=0; i<floors.length; i++){
			dbo.table.add(floors[i].toString(), new Floor(
					floors[i],
					Scheduler.dbo.find(floors[i]),
					CurrencyPair.dbo.find(floors[i])
			));
		}
	}
}
