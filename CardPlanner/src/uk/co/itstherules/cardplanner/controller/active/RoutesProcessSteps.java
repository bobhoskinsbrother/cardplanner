package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.controller.processor.ProcessStepIOProvider;
import uk.co.itstherules.cardplanner.view.CardPlannerTagBuilder;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.string.manipulation.CollectionConverter;
import uk.co.itstherules.yawf.EnumArrayToEnumMapConverter;
import uk.co.itstherules.yawf.controller.processor.ProcessStep.ProcessStepType;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.ProcessStepModel;
import uk.co.itstherules.yawf.model.RouteModel;
import uk.co.itstherules.yawf.model.SimpleEntityModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.helper.UrlBuilder;

public class RoutesProcessSteps extends CardPlannerBase<RouteModel> {

	@Override protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, RouteModel object) throws IOException {
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
		String editUrl = new UrlBuilder(root).edit(getTitle(), provider.getIdentity(), "index.xhtml", Collections.<Entity<?>>singletonList(new SimpleEntityModel("saved", "true")));
		context.put("redirect", editUrl);
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
	
	@Override
    public RouteModel getDefaultModel(ObjectCache objectCache) {
	    return new RouteModel().defaultSetup(objectCache);
    }

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, RouteModel object, QueryKeyViolations violations) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		RouteModel route = objectCache.retrieveByIdentity(RouteModel.class, provider.getIdentity());
		Set<ProcessStepModel> processSteps = objectCache.all(ProcessStepModel.class, ObjectState.Active);
		
		context.put("saved", provider.getBoolean("saved", Boolean.FALSE));
		context.put("route", route);
		context.put("collectionConverter", new CollectionConverter());
		context.put("action", action);
		ProcessStepIOProvider processStepIO = new ProcessStepIOProvider();
		context.put("processStepIO", processStepIO);
		context.put("processSteps", processSteps);
		context.put("ProcessStepType", new EnumArrayToEnumMapConverter().convert(ProcessStepType.values()));
		context.put("tagBuilder", new CardPlannerTagBuilder(root));
		View view = new MergedTextView("routesprocesssteps/change.freemarker");
		String text = view.asText(context , root);
		getTemplate(text, getTitle(), "Route Process Steps").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

}
