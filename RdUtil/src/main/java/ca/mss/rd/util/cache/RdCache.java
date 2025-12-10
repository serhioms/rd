package ca.mss.rd.util.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.io.UtilFile;


public class RdCache<Key, Value> {

	final static public String module = RdCache.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public String CACHE_FOLDER = "data";

	final private String cacheIdent;
	final Map<Key, Value> cache;

	final static private Map<String,RdCache> list = new HashMap<String,RdCache>();

	public RdCache(String cacheIdent) {
		this.cacheIdent = cacheIdent;

		if( list.containsKey(cacheIdent) ){
			this.cache = list.get(cacheIdent).cache; // read from the same map everywhere!!!
		} else {
			list.put(cacheIdent, this);
			this.cache = new HashMap<Key, Value>();
		}
	}

	final private String getCacheFile(){
		return CACHE_FOLDER+File.separator+cacheIdent+".properties";
	}
	
	final public void put(Key key, Value value){
		cache.put(key, value);
	}

	final public Value get(Key key){
		return cache.get(key);
	}

	final public void remove(Key key){
		cache.remove(key);
	}

	final public boolean contains(Key key){
		return cache.containsKey(key);
	}

	
	final public void save(){
		Properties data = new Properties();
		
		UtilProperty.readProperties(getCacheFile(), data);
		
		for(Iterator<Entry<Key, Value>>iter = cache.entrySet().iterator(); iter.hasNext(); ){
			Entry<Key, Value> entry = iter.next();
			data.setProperty(entry.getKey().toString(), entry.getValue().toString());
		}
		UtilProperty.writeProperties(getCacheFile(), data);
	}
	
	final public void read(){
		Properties data = new Properties();

		UtilProperty.readProperties(getCacheFile(), data);
		
		for(Iterator<Entry<Object, Object>>iter = data.entrySet().iterator(); iter.hasNext(); ){
			Entry<Object, Object> entry = iter.next();
			cache.put(castKey(entry.getKey().toString()), castValue(entry.getValue().toString()));
		}

		try {
			UtilProperty.writeProperties(getCacheFile(), data);
		}catch(Throwable t){
			UtilFile.createFile(getCacheFile());
			UtilProperty.readProperties(getCacheFile(), data);
		}
	}

	public Key castKey(String str){
		return (Key )str;
	}
	
	public Value castValue(String str){
		return (Value )str;
	}
	
	public Iterator<Entry<Key, Value>> iterator(){
		return cache.entrySet().iterator();
	}
	
	final public void clear(){
		cache.clear();
	}

	final static public void clearAll(){
		for(Iterator<RdCache> iter = list.values().iterator(); iter.hasNext(); ){
			iter.next().clear();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RdCache<String,String> cache = new RdCache<String,String>("test");
		
		cache.put("item1", "test1");
		cache.put("item2", "test2");
		
		cache.save();

		cache.clear();
		
		cache.read();
		
		for(Iterator<Entry<String, String>>iter = cache.iterator(); iter.hasNext(); ){
			Entry<String, String> entry = iter.next();
			System.out.println(entry.getKey().toString()+" = "+entry.getValue().toString());
		}
		
	}

}
