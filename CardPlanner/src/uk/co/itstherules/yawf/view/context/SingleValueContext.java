package uk.co.itstherules.yawf.view.context;

import java.util.Collections;


public final class SingleValueContext extends BaseContext {
	
	public SingleValueContext(String key, Object value) {
		super(new MapContext(Collections.singletonMap(key, value)));
    }

}
