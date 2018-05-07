package uk.co.itstherules.yawf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MapBuilder<K, V> {
	
	private final Map<K, V> map;
	
	public MapBuilder() {
		this.map = new ConcurrentHashMap<K, V>();
    }
	
	public MapBuilder<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}
	
	public Map<K, V> build() { return map; }
	
}
