package uk.co.itstherules.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesMap implements Map<String, Object> {
	
	private final Map<String, Object> delegate;
	
	public PropertiesMap(Properties properties) {
		Set<Object> keySet = properties.keySet();
		this.delegate = new HashMap<String, Object>();
		for (Object object : keySet) {
	        this.delegate.put(String.class.cast(object), properties.get(object));
        }
    }

	public void clear() {
	    delegate.clear();
    }

	public boolean containsKey(Object obj) {
	    return delegate.containsKey(obj);
    }

	public boolean containsValue(Object obj) {
	    return delegate.containsValue(obj);
    }

	public Set<Map.Entry<String, Object>> entrySet() {
	    return delegate.entrySet();
    }

	public boolean equals(Object obj) {
	    return delegate.equals(obj);
    }

	public Object get(Object obj) {
	    return delegate.get(obj);
    }

	public int hashCode() {
	    return delegate.hashCode();
    }

	public boolean isEmpty() {
	    return delegate.isEmpty();
    }

	public Set<String> keySet() {
	    return delegate.keySet();
    }

	public Object put(String key, Object value) {
	    return delegate.put(key, value);
    }

	public void putAll(Map<? extends String, ? extends Object> t) {
	    delegate.putAll(t);
    }

	public Object remove(Object obj) {
	    return delegate.remove(obj);
    }

	public int size() {
	    return delegate.size();
    }

	public Collection<Object> values() {
	    return delegate.values();
    }

}
