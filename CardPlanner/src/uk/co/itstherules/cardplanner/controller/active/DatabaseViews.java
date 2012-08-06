package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.EnumArrayToEntityListConverter;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.TemplateModel;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateMarkup;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateModelType;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateType;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.SingleValueContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class DatabaseViews extends CardPlannerBase<TemplateModel> {

	protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, TemplateModel object) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, object);
		object.setTemplateType(TemplateType.FromDatabase);
		object.setModelType(TemplateModelType.Body);
		if(!violations.isRegistered()) {
			objectCache.save(object);
			String root = provider.getApplicationRoot();
            ViewContext context = new SingleValueContext("redirect", provider.getString("redirect", listUrl(root)));
            new MergedTextView("done.freemarker").renderTo(context, response, root);
		} else {
			changeView(objectCache, provider, response, viewFactory, action, object, violations);
		}
    }
	
	@Override protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, TemplateModel object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("databaseviews/change.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, object);
		String root = provider.getApplicationRoot();
		context.put("templateMarkups", new EnumArrayToEntityListConverter().convert(TemplateMarkup.values()));
		context.put("redirect", provider.getString("redirect", listUrl(root)));
		context.put("instance", object);
		new TemplateCompositeModelView(true, view.asText(context, root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }
	
	@Override protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new SingleValueContext("redirect", provider.getString("redirect", listUrl(root)));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}

	@Override public TemplateModel getDefaultModel(ObjectCache objectCache) {
	    return new TemplateModel().defaultSetup(objectCache);
    }

}
