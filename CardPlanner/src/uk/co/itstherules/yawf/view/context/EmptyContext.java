package uk.co.itstherules.yawf.view.context;

import java.util.HashMap;


public final class EmptyContext extends BaseContext {
	
	public EmptyContext() {
		super(new MapContext(new HashMap<String, Object>()));
    }

}
