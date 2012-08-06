package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.EnumArrayToEntityListConverter;
import uk.co.itstherules.yawf.EnumArrayToEnumMapConverter;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.TemplateModel;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateMarkup;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateModelType;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateType;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;



public final class Templates extends CardPlannerBase<TemplateModel> {

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, TemplateModel object, QueryKeyViolations violations) throws IOException {
		ViewContext context = new EmptyContext();
		context.put("TemplateMarkup", new EnumArrayToEnumMapConverter().convert(TemplateMarkup.values()));
		context.put("templateMarkups", new EnumArrayToEntityListConverter().convert(TemplateMarkup.values()));
		context.put("TemplateType", new EnumArrayToEnumMapConverter().convert(TemplateType.values()));
		context.put("templateTypes", new EnumArrayToEntityListConverter().convert(TemplateType.values()));
		context.put("ModelType", new EnumArrayToEnumMapConverter().convert(TemplateModelType.values()));
		context.put("modelTypes", new EnumArrayToEntityListConverter().convert(TemplateModelType.values()));
	}

	@Override
    public TemplateModel getDefaultModel(ObjectCache objectCache) { 
		return new TemplateModel().defaultSetup(objectCache);
	}
	
	@Override
	protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("redirect", provider.getString("redirect", listUrl(root)));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
}
