package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.StringCollectionToEntityListConverter;
import uk.co.itstherules.yawf.controller.processor.ProcessStepRegisterListener;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ProcessStepModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class ProcessSteps extends CardPlannerBase<ProcessStepModel> {

	
	@Override @Action("Delete")
	public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	objectCache.delete(identityModelFromCache(objectCache, provider));
    	try {
    		String url = response.encodeRedirectURL(provider.getString("redirect", listUrl(provider.getApplicationRoot())));
			response.sendRedirect(url);
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
	}
	
	@Override protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, ProcessStepModel object) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, object);
		if(!violations.isRegistered()) {
			objectCache.save(object);
			sendChangeActionRedirect(provider, response);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, object, violations);
		}
	}

	@Override protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		ViewContext context = new EmptyContext();
		String root = provider.getApplicationRoot();
		context.put("redirect", provider.getString("redirect", listUrl("index.xhtml", root)));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
	
	@Override public ProcessStepModel getDefaultModel(ObjectCache objectCache) {
	    return new ProcessStepModel().defaultSetup(objectCache);
    }

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, ProcessStepModel object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("processsteps/change.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		Set<String> availableSteps = ProcessStepRegisterListener.availableKeys();
		context.put("violations", violations);
		context.put("availableSteps", new StringCollectionToEntityListConverter().convert(availableSteps));
		context.put("processStep", object);
		context.put("action", action);
		context.put("redirect", provider.getString("redirect", listUrl("index.xhtml", root)));
		String text = view.asText(context , root);
		getPop(text, getTitle(), action + " Process Step").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

}
