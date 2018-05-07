package uk.co.itstherules.cardplanner.controller;

import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.TextStringView;
import uk.co.itstherules.yawf.view.context.EmptyContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class CardPlannerBase<T extends IdentityDeleteable<T>> extends BaseController {

	public abstract T getDefaultModel(ObjectCache objectCache);
	
	@Action("Add") public void add(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		changeView(objectCache, provider, response, viewFactory, "Create", getDefaultModel(objectCache), new QueryKeyViolations());
    }

	@Action("Edit") public void edit(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	changeView(objectCache, provider, response, viewFactory, "Update", identityModelFromCache(objectCache, provider), new QueryKeyViolations());
    }
    
	@Action("Create") public void create(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	changeAction(objectCache, provider, response, viewFactory, "Create", getDefaultModel(objectCache));
	}
    
	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	T model = identityModelFromCache(objectCache, provider);
    	bind(objectCache, provider, model);
    	changeAction(objectCache, provider, response, viewFactory, "Update", model);
    }
    
	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	objectCache.delete(identityModelFromCache(objectCache, provider));
    	try {
    		response.sendRedirect(response.encodeRedirectURL(provider.getString("redirect", listUrl(provider.getApplicationRoot()))));
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }
	
	@Action("Show") public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	String root = provider.getApplicationRoot();
		String view = showView(identityModelFromCache(objectCache, provider),root);
    	getTemplate(view, getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

	protected ModelView getTemplate(String text, String controller, String title) {
		return new TemplateCompositeModelView(false, text, controller, title);
    }

	protected ModelView getPop(String text, String controller, String title) {
		return new TemplateCompositeModelView(true, text, controller, title);
    }

	protected String showView(T identityModelFromCache, String root) {
		return new TextStringView("No Show View Found").asText(new EmptyContext(), root);
    }

	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		changeView(objectCache, provider, response, viewFactory, "Create", getDefaultModel(objectCache), new QueryKeyViolations());
	}
	
	protected abstract void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, T object, QueryKeyViolations violations) throws IOException;
	
	protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, T object) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, object);
		if(!violations.isRegistered()) {
			objectCache.save(object);
			sendChangeActionRedirect(provider, response);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, object, violations);
		}
    }

	protected QueryKeyViolations bind(ObjectCache objectCache, ValuesProvider provider, T object) {
	    return new BasicValuesProviderBinder().bind(provider, object, objectCache);
    }

	protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		response.sendRedirect(response.encodeRedirectURL(provider.getString("redirect", listUrl(provider.getApplicationRoot()))));
    }
	
	protected T identityModelFromCache(ObjectCache objectCache, ValuesProvider provider) {
    	T model = getDefaultModel(objectCache);
    	model.setIdentity(provider.getIdentity());
    	model = objectCache.retrieveByIdentity(model);
    	return model;
	}
}
