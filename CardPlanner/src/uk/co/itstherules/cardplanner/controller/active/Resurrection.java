package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.model.ResurrectableThing;
import uk.co.itstherules.cardplanner.model.ResurrectableThings;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


public final class Resurrection extends BaseController {

	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		ResurrectableThings resurrectableThings = new ResurrectableThings();
		Set<ResurrectableThing> values = resurrectableThings.all();
		String identity = provider.getIdentity();
		ResurrectableThing 
		resurrectableThing = resurrectableThings.first();
		if(!identity.equals("0")){
			resurrectableThing = resurrectableThings.get(identity);
		}
		Class<? extends IdentityDeleteable<?>> modelType = resurrectableThing.get();
		Set<? extends IdentityDeleteable<?>> all = objectCache.all(modelType, ObjectState.Deleted);
		View view = new MergedTextView("resurrection/list.freemarker");
		
		ViewContext context = new EmptyContext();
		context.put("rezs", values);
		context.put("resurrectableThing", resurrectableThing);
		context.put("all", all);

		String root = provider.getApplicationRoot();
		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Action("Update")
	public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String thingIdentity = provider.getString("type");
		ResurrectableThings resurrectableThings = new ResurrectableThings();
		ResurrectableThing thing = resurrectableThings.get(thingIdentity);
		Class<? extends IdentityDeleteable<?>> classType = thing.get();
		IdentityDeleteable<?> retrieved = objectCache.retrieveByIdentity(classType, provider.getIdentity(), ObjectState.Deleted);
		retrieved.activate();
		objectCache.save(retrieved);
		response.sendRedirect(response.encodeRedirectURL(listUrl("index.xhtml", thing.getIdentity(), provider.getApplicationRoot())));
	}
}
