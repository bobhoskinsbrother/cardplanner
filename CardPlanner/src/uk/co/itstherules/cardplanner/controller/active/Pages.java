package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.EntityComparator;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.TemplateModel;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateModelType;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateType;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;



public final class Pages extends CardPlannerBase<PageModel> {

	
	@Override @Action("Show")
	public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		PageModel pageModel = objectCache.<PageModel>retrieveByIdentity(PageModel.class, provider.getIdentity());
		new PageModelView(pageModel, viewFactory).renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Override @Action("List")
	public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		View view = new MergedTextView("pages/list.freemarker");
		ViewContext context = new EmptyContext();
		String root = provider.getApplicationRoot();
		Set<PageModel> pages = objectCache.all(PageModel.class);
		pages.remove(SpecialInstances.retrieve(objectCache, Identities.INVISIBLE_PAGE));
		context.put("pages", pages);
		context.put("templates", objectCache.all(TemplateModel.class));
		context.put("databaseViews", objectCache.all(TemplateModel.class, "templateType", TemplateType.FromDatabase));

		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, PageModel object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("pages/change.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, object);
		String root = provider.getApplicationRoot();
		Set<PageModel> parents = objectCache.all(PageModel.class);
		List<TemplateModel> templates = new ArrayList<TemplateModel>(objectCache.all(TemplateModel.class, "modelType", TemplateModelType.Frame));
		List<TemplateModel> bodies = new ArrayList<TemplateModel>(objectCache.all(TemplateModel.class, "modelType", TemplateModelType.Body));
		
		Collections.sort(templates, new EntityComparator());
		Collections.sort(bodies, new EntityComparator());

		
		context.put("redirect", provider.getString("redirect", listUrl(root)));
		context.put("page", object);
		context.put("parents", parents);
		context.put("templates", templates);
		context.put("bodies", bodies);
		new TemplateCompositeModelView(true, view.asText(context, root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

	@Override
    public PageModel getDefaultModel(ObjectCache objectCache) { 
		return new PageModel().defaultSetup(objectCache);
	}
	
	@Override
	protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("redirect", provider.getString("redirect", listUrl(root)));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
}
