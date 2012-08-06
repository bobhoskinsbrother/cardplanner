package uk.co.itstherules.yawf.view;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public final class ResourceBundleBag {

	public Map<String, String> asMap(ResourceBundle bundle) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
	        String key = keys.nextElement();
	        map.put(key, bundle.getString(key));
	        
        }
	    return map;
    }
}
