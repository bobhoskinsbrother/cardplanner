package uk.co.itstherules.yawf.view.context;

import java.util.Map;

public interface ViewContext {
	
	public Object put(String key, Object value);
	public void putAll(ViewContext viewContext);
	public Map<String, Object> getStore();
	public Object get(String key);

}
