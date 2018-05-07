package uk.co.itstherules.yawf.inbound;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class MapValuesProvider extends AbstractValuesProvider {

	private final Map<String, Object> map;

	public MapValuesProvider(Map<String, Object> map) {
		this(map, new DefaultValuesProvider());
    }
	
	public MapValuesProvider(Map<String, Object> map, ValuesProvider delegate) {
		super(delegate);
		this.map = map;
    }

	@Override
    public Set<String> getKeys() {
		LinkedHashSet<String> collector = new LinkedHashSet<String>();
		collector.addAll(this.map.keySet());
		collector.addAll(this.delegate.getKeys());
		return collector;
    }

	@Override
    public String getString(String key, String defaultString) {
	    String reply = String.class.cast(this.map.get(key));
		if(reply != null && !"".equals(reply)) { return reply; }
		return this.delegate.getString(key, defaultString); 
    }	
}