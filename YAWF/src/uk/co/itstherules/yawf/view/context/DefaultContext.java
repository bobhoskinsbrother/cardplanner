package uk.co.itstherules.yawf.view.context;


public final class DefaultContext extends BaseContext {
	
	public DefaultContext(String controller, String action) {
		super(new EmptyContext());
		delegate.put("controller", controller);
		delegate.put("action", action);
		delegate.put("title", controller + " " + action);
    }

}
