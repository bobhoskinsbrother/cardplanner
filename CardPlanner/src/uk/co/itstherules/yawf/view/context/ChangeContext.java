package uk.co.itstherules.yawf.view.context;

import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;

public final class ChangeContext extends BaseContext{
	public ChangeContext(String controller, String action, QueryKeyViolations violations, Object object) {
		super(new DefaultContext(controller, action));
		delegate.put("violations", violations);
		delegate.put("instance", object);
    }
}
