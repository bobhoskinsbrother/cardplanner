package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


public final class Attachments extends CardPlannerBase<SimpleAttachmentModel> {

	protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, SimpleAttachmentModel object) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, object);
		if(!violations.isRegistered()) {
			objectCache.saveExternal(object);
			sendChangeActionRedirect(provider, response);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, object, violations);
		}
    }
	
	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	SimpleAttachmentModel model = identityModelFromCache(objectCache, provider);
		objectCache.delete(model);
    	try {
    		response.sendRedirect(response.encodeRedirectURL(listUrl(provider.getApplicationRoot())));
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }

	@Action("Show") public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		SimpleAttachmentModel attachment = identityModelFromCache(objectCache, provider);
		try {
			if("Thumbnail".equals(provider.getTitle())) {
				attachment.streamThumbnailTo(response.getOutputStream());
			} else {
				attachment.streamTo(response.getOutputStream());
			}
		} catch(Exception e) {
			throw new RuntimeException("Unable to show attachment: " + e.getCause().getMessage());
		}
	}

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider,  HttpServletResponse response, ModelViewRegister viewFactory, String action, SimpleAttachmentModel object, QueryKeyViolations violations) throws IOException {
		Set<SimpleAttachmentModel> attachments = objectCache.all(SimpleAttachmentModel.class);
		attachments.remove(SpecialInstances.retrieve(objectCache, Identities.DEFAULT_ATTACHMENT));
		ViewContext context = new EmptyContext();
		context.put("attachments", attachments);
		context.put("attachment", object);
		context.put("action", action);
		context.put("violations", violations);
		View view = new MergedTextView("attachments/list.freemarker");
		String root = provider.getApplicationRoot();
		String text = view.asText(context, root);
		getPop(text, getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

	@Override
    public SimpleAttachmentModel getDefaultModel(ObjectCache objectCache) {
		return new SimpleAttachmentModel().defaultSetup(objectCache);
    }
}
