package uk.co.itstherules.yawf.view.context;

import java.util.Map;


public final class MapContext implements ViewContext {
	
	private final Map<String, Object> delegate;

	public MapContext(Map<String, Object> map) {
		this.delegate = map;
    }
	public Map<String, Object> getStore() {
		return delegate;
	}

	public Object put(String key, Object value) {
		return delegate.put(key, value);
	}

	public void putAll(ViewContext viewContext) {
		delegate.putAll(viewContext.getStore());
	}
	
	public Object get(String key) {
		return delegate.get(key);
    }
}
