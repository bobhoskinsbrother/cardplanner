package uk.co.itstherules.yawf.controller;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.controller.processor.RouteProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheFactory;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class RouteProcessStepController implements Controller {
	
	@Override public void control(ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
		ObjectCache objectCache = ObjectCacheFactory.get();
		new RouteProcessStep().process(objectCache, provider, response, viewFactory, new QueryKeyViolations());
		objectCache.close();
	}

	@Override
	public String getTitle() {
		return "RouteProcessStepController";
	}

	@Override
	public String getKey() {
		return "RouteProcessStepController";
	}
}
