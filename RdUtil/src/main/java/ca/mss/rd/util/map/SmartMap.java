package ca.mss.rd.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

abstract public class SmartMap<K, V> implements Map<Object, V> {

	private final Map<Object, V> map; 

	public SmartMap() {
		this(0);
	}

	public SmartMap(int initialCapacity) {
		map = SmartMap.<Object, V>mapFactory(0);
	}

	@Override
	public V get(Object key) {
		V val = map.get(key);
		
		if( val == null ){
			val = valueFactory(key);
			map.put(key, val);
		}
		
		return val;	
	}

	abstract public V valueFactory(Object key);
	
	static public <K,V> Map<K, V> mapFactory(int initialCapacity ){
		return new ConcurrentHashMap<K, V>(initialCapacity);
	}

	/*
	 * Generated from HashMap 
	 */
	
	
	@Override
	public int size() {
		
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		
		return map.containsKey(key);
	}

	@Override
	public V put(Object key, V value) {
		
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends Object, ? extends V> m) {
		
		map.putAll(m);
	}

	@Override
	public V remove(Object key) {
		
		return map.remove(key);
	}

	@Override
	public void clear() {
		
		map.clear();
	}

	@Override
	public boolean containsValue(Object value) {
		
		return map.containsValue(value);
	}

	@Override
	public Set<Object> keySet() {
		
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<Object, V>> entrySet() {
		
		return map.entrySet();
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		
		return map.getOrDefault(key, defaultValue);
	}

	@Override
	public V putIfAbsent(Object key, V value) {
		
		return map.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		
		return map.remove(key, value);
	}

	@Override
	public boolean replace(Object key, V oldValue, V newValue) {
		
		return map.replace(key, oldValue, newValue);
	}

	@Override
	public V replace(Object key, V value) {
		
		return map.replace(key, value);
	}

	@Override
	public V computeIfAbsent(Object key, Function<? super Object, ? extends V> mappingFunction) {
		
		return map.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public V computeIfPresent(Object key, BiFunction<? super Object, ? super V, ? extends V> remappingFunction) {
		
		return map.computeIfPresent(key, remappingFunction);
	}

	@Override
	public V compute(Object key, BiFunction<? super Object, ? super V, ? extends V> remappingFunction) {
		
		return map.compute(key, remappingFunction);
	}

	@Override
	public V merge(Object key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		
		return map.merge(key, value, remappingFunction);
	}

	@Override
	public void forEach(BiConsumer<? super Object, ? super V> action) {
		
		map.forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super Object, ? super V, ? extends V> function) {
		
		map.replaceAll(function);
	}

	@Override
	public boolean equals(Object o) {
		
		return map.equals(o);
	}

	@Override
	public int hashCode() {
		
		return map.hashCode();
	}

	@Override
	public String toString() {
		
		return map.toString();
	}

}
