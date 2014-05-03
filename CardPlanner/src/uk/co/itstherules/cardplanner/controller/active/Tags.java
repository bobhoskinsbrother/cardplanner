package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.TagModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.controller.ContentType;
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
import uk.co.itstherules.yawf.view.json.JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Tags extends CardPlannerBase<TagModel> {

	@Override public TagModel getDefaultModel(ObjectCache objectCache) { return new TagModel().defaultSetup(objectCache); }
	
	@Override @Action("List")
	public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		View view = new MergedTextView("tags/list.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("tags", objectCache.all(TagModel.class));
		context.put("cards", objectCache.all(CardModel.class));
		getTemplate(view.asText(context, root), getTitle(), "List Tags").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }
	
	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, TagModel tag, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("tags/edit.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new ChangeContext(getTitle(), action, violations, tag);
		context.put("tag", tag);
		getPop(view.asText(context, root), getTitle(), action+" A Tag").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }
	
	@Override
	protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("redirect", listUrl(root));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}

	@Action("Feed")
	public void feed(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<TagModel> tags = objectCache.all(TagModel.class, new QueryConditions("AND").put(ProviderKey.title.name(), Operator.Like, provider.getTitle()));
		Map<String, Object> tagsMap = new HashMap<String, Object>();
		tagsMap.put("tags", tags);
		response.setContentType(ContentType.json.toString());
		new JsonView(tagsMap).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
	}
}