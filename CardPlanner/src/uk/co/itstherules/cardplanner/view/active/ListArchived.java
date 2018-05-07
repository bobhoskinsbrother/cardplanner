package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.model.PostItModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ListArchived extends BaseModelView {

	@Override public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		String root = provider.getApplicationRoot();
		Set<CardModel> cards = objectCache.all(CardModel.class, ObjectState.Archived);
		Set<PostItModel> postIts = objectCache.all(PostItModel.class, ObjectState.Archived);
		View view = new MergedTextView("archived/list.freemarker");
        ViewContext context = new EmptyContext();
		if(mixInContext!=null) {context.putAll(mixInContext);}
        context.put("postIts", postIts);
        context.put("cards",  cards);
        context.put("root", root);
	    context.put("types", objectCache.all(CardTypeModel.class));
        List<String> cssList = Arrays.asList("storyboard", "humane");
        List<String> javascriptList = Arrays.asList("browserintelligence_1_0", "pxdecorator_1_0", "humane");
        return new TemplateCompositeModelView(false,  view.asText(context, root), getKey(), "", cssList, javascriptList).asText(objectCache, provider, new EmptyContext(), violations);
    }

}
