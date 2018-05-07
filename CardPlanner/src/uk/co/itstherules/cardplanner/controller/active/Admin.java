package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;

public final class Admin extends BaseController {

	@Action("Show") public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		viewFactory.get("ShowAdmin").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

}