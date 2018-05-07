package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.model.LogModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Logs extends BaseController {

	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		List<LogModel> all = new ArrayList<LogModel>(objectCache.all(LogModel.class));
		Collections.sort(all);
		ViewContext context = new EmptyContext();
		context.put("logs", all);
		String root = provider.getApplicationRoot();
		context.put(ProviderKey.root.name(), root);
		View view = new MergedTextView("logs/list.freemarker");
		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

}
