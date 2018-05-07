package uk.co.itstherules.yawf.view.context;

import java.util.Map;

public abstract class BaseContext implements ViewContext {
	
	protected ViewContext delegate;
	
	public BaseContext(ViewContext delegate) {
		this.delegate = delegate;
    }

	public Object put(String key, Object value) {
	    return delegate.put(key, value);
    }

	public void putAll(ViewContext m) {
	    delegate.putAll(m);
    }
	
	public Map<String, Object> getStore(){
		return delegate.getStore();
	}

	public Object get(String key) {
		return delegate.get(key);
    }

}
