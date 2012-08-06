package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.view.CardPlannerTagBuilder;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.CountryModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class Countries extends CardPlannerBase<CountryModel> {

	
	@Override
	protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CountryModel object) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, object);
		if(!violations.isRegistered()) {
			objectCache.save(object);
			sendChangeActionRedirect(provider, response);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, object, violations);
		}
	}
	
	@Override
	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		Set<CountryModel> countries = objectCache.all(CountryModel.class);
		context.put("countries", countries);
		context.put("tagBuilder", new CardPlannerTagBuilder(root));
		View view = new MergedTextView("countries/list.freemarker");
		String text = view.asText(context , root);
		getTemplate(text, getTitle(), "Countries List").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

	@Override protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		ViewContext context = new EmptyContext();
		String root = provider.getApplicationRoot();
		context.put("redirect", listUrl("index.xhtml", root));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
	
	@Override
    public CountryModel getDefaultModel(ObjectCache objectCache) {
	    return new CountryModel().defaultSetup(objectCache);
    }

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CountryModel object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("countries/change.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("violations", violations);
		context.put("country", object);
		context.put("action", action);
		String text = view.asText(context , root);
		getPop(text, getTitle(), action + " Country").renderTo(objectCache, provider, response, new EmptyContext(), violations);

    }

}
