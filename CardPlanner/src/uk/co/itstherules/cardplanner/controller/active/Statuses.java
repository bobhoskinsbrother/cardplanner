package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.cardplanner.model.StoryBoardService;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.model.persistence.QueryConditions.Operator;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;



public final class Statuses extends CardPlannerBase<StatusModel> {
	
	@Override public StatusModel getDefaultModel(ObjectCache objectCache) { return new StatusModel().defaultSetup(objectCache); }

	
	@Override @Action("List")
	public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<StatusModel> statuses = objectCache.all(StatusModel.class, new QueryConditions("AND").put("identity", Operator.NotEquals, Identities.THE_BACKLOG.getIdentity()));
		View view = new MergedTextView("statuses/list.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("statuses", statuses);
		getPop(view.asText(context, root), getTitle(), "List Statuses").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Override
	protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
        rerouteResponseTo(response, provider.getApplicationRoot(), "List");
	}
	
	@Override
	protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, StatusModel status, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("statuses/edit.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new ChangeContext(getTitle(), action, violations, status);
		context.put("status", status);
		context.put("theBacklog", "TheBacklog");
		new TemplateCompositeModelView(true, view.asText(context, root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), violations);
	}
	
	protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, StatusModel status) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, status);
		if(!violations.isRegistered()) {
			objectCache.save(status);
            String serializedStatus = SerializeModel.status(status);
			getClient(provider.getString("clientIdentity")).shareObject(StatusModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedStatus));
			sendChangeActionRedirect(provider, response);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, status, violations);
		}
    }
    
	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	StatusModel status = identityModelFromCache(objectCache, provider);
        new StoryBoardService().deleteHotspotsWith(objectCache, status);
        objectCache.delete(status);
        String serializedStatus = SerializeModel.status(status);
		getClient(provider.getString("clientIdentity")).shareObject(StatusModel.class, new SharedObject(SharedObject.Action.DELETE, serializedStatus));
        rerouteResponseTo(response, provider.getApplicationRoot(), "List");
    }

	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }


}
