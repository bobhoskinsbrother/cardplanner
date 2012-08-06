package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.controller.processor.ProcessStepIOProvider;
import uk.co.itstherules.cardplanner.view.CardPlannerTagBuilder;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.EnumArrayToEnumMapConverter;
import uk.co.itstherules.yawf.controller.processor.ProcessStep.ProcessStepType;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.ProcessStepModel;
import uk.co.itstherules.yawf.model.RouteModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class Routes extends CardPlannerBase<RouteModel> {

	
	@Override protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, RouteModel object) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, object);
		if(!violations.isRegistered()) {
			objectCache.save(object);
			sendChangeActionRedirect(provider, response);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, object, violations);
		}
	}
	
	@Override @Action("List")
	public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		Set<RouteModel> routes = objectCache.all(RouteModel.class, ObjectState.Active);
		Set<ProcessStepModel> processSteps = objectCache.all(ProcessStepModel.class, ObjectState.Active);
		
		context.put("routes", routes);
		context.put("processStepIO", new ProcessStepIOProvider());
		context.put("processSteps", processSteps);
		context.put("ProcessStepType", new EnumArrayToEnumMapConverter().convert(ProcessStepType.values()));
		context.put("tagBuilder", new CardPlannerTagBuilder(root));
		View view = new MergedTextView("routes/list.freemarker");
		String text = view.asText(context , root);
		getTemplate(text, getTitle(), "Routes List").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

	@Override protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		ViewContext context = new EmptyContext();
		String root = provider.getApplicationRoot();
		context.put("redirect", listUrl("index.xhtml", root));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
	
	@Override
    public RouteModel getDefaultModel(ObjectCache objectCache) {
	    return new RouteModel().defaultSetup(objectCache);
    }

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, RouteModel object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("routes/change.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("violations", violations);
		context.put("route", object);
		context.put("action", action);
		String text = view.asText(context , root);
		getPop(text, getTitle(), action + " Route").renderTo(objectCache, provider, response, new EmptyContext(), violations);

    }

}
