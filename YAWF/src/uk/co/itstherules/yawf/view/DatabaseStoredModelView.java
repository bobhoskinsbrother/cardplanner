package uk.co.itstherules.yawf.view;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

@Entity 
public final class DatabaseStoredModelView extends IdentifiableDeleteableModel<DatabaseStoredModelView> implements ModelView {

	@QueryKey("content") private String content;
	@QueryKey("key") private String key;
	
	public String getContent() { return content; }

	public ViewContext getModel(ObjectCache objectCache) {
	    EmptyContext context = new EmptyContext();
		return context;
    }

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		return new TextStringView(content).asText(getModel(objectCache), valuesProvider.getApplicationRoot());
    }

	@Override
    public String getKey() {
	    return key;
    }

	@Override
    public String getViewTitle() {
	    return title;
    }

	@Override
    public void renderTo(ObjectCache objectCache, ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations) {
		new TextStringView(content).renderTo(getModel(objectCache), response, valuesProvider.getApplicationRoot());
    }

    @Override public DatabaseStoredModelView defaultSetup(ObjectCache objectCache) { return this; }


}
