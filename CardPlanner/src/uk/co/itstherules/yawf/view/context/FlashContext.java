package uk.co.itstherules.yawf.view.context;


public final class FlashContext extends BaseContext {
	public FlashContext(String controller, String action, String root, String name, Integer width, Integer height) {
		super(new DefaultContext(controller, action));
		delegate.put("name", name);
		delegate.put("root", root);
		delegate.put("width", width);
		delegate.put("height", height);
    }
}
