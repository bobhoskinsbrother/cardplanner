package uk.co.itstherules.yawf.modelview;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class CompositeView implements ModelView {
	
	private List<ModelView> views;

	public CompositeView() {
		this.views = new ArrayList<ModelView>();
    }
	
	public void add(ModelView view) {
		views.add(view);
    }
	
	public void renderTo(ObjectCache objectCache, ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations) {
		for (ModelView view : views) { view.renderTo(objectCache, valuesProvider, response, null, violations); }
	}

	public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		StringBuffer buffer = new StringBuffer();
		for (ModelView view : views) { 
			buffer.append(view.asText(objectCache, valuesProvider, null, violations)); 
		}
		return buffer.toString();
	}
	
    public String getKey() { return this.getClass().getSimpleName(); }
    @Override public String getViewTitle() { return ""; }

}
