package ca.mss.rd.table;

import java.util.List;

public class RdDBO<T> {

	final public RdTable<T> table;

	public RdDBO(RdTable<T> table) {
		super();
		this.table = table;
	}

	final public T find(Object pk){
		return table.find(pk.toString());
	}
	
	final public List<T> select(Object pk){
		return table.select(pk.toString());
	}
}
