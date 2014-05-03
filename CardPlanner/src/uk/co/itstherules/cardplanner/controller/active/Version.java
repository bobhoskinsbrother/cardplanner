package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public final class Version extends BaseController {

	@Action("Show")
	public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		new TemplateCompositeModelView(false, provider.getString("applicationVersion"), getTitle(), "Application Version").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

}
