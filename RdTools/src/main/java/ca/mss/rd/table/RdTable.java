package ca.mss.rd.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RdTable<T> extends HashMap<String, T> {

	final public T find(String pk){
		return get(pk);
	}

	final public List<T> select(String pk){
		List<T> subset = new ArrayList<T>();
		T elem;
		for(int i=0; i<1000; i++){
			if( (elem=get(pk+i)) != null )
				subset.add(elem);
			else
				break;
		}
		return subset;
	}
	
	final public void add(String pk, T elem){
		put(pk, elem);
	}

	final public void add(String pk, T[] elem){
		for(int i=0; i<elem.length; i++){
			put(pk+i, elem[i]);
		}
	}

	final public void add(String pk, List<T> elem){
		for(int i=0,length=elem.size(); i<length; i++){
			put(pk+i, elem.get(i));
		}
	}
}
