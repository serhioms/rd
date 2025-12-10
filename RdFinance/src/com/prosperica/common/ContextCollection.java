package com.prosperica.common;

import java.util.ArrayList;
import java.util.List;

public class ContextCollection {

	private List<CommonContext> list = new ArrayList<CommonContext>();
	public int historyIndex = 0;

	final public  ContextCollection add(CommonContext context){
		list.add(context);
		return this;
	}

	final public boolean hasNext(){
		return historyIndex < list.size();
	}

	final public CommonContext getNext(){
		return list.get(historyIndex);
	}

	final public CommonContext getLast(CommonContext last){
		return list.size() > 0? list.get(list.size()-1): last;
	}

	final public void increment(){
		historyIndex += 1;
	}

	final public void setCollection(List<CommonContext> list){
		this.list = list;
	}

	final public List<CommonContext> getCollection(){
		return list;
	}

}
