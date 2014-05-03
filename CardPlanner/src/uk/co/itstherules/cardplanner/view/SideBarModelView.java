package uk.co.itstherules.cardplanner.view;

import uk.co.itstherules.cardplanner.model.TagModel;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class SideBarModelView extends BaseModelView {

	private String template() {
		return "sidebar.freemarker";
	}

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		if(objectCache==null) { throw new IllegalArgumentException("ObjectCache needs to be set.  Have you set requiresObjectCache to true in your controller?"); }
		ViewContext context = new EmptyContext();
        context.put("tags", objectCache.all(TagModel.class));
        context.put("modulesNav", ModuleNavigationRegister.getView().asText(objectCache, valuesProvider, null, violations));

	    return new MergedTextView(template()).asText(context, valuesProvider.getApplicationRoot());
    }
	
}