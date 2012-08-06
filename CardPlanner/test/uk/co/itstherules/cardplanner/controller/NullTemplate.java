package uk.co.itstherules.cardplanner.controller;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class NullTemplate implements ModelView {
	
	private boolean asTextCalled = false;
	private boolean renderToCalled = false;
	
	public boolean isAsTextCalled() {
	    return asTextCalled;
    }
	
	public boolean isRenderToCalled() {
	    return renderToCalled;
    }
    public String getKey() { return this.getClass().getSimpleName(); }

	public String asText(ObjectCache objectCache,
            ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		asTextCalled = true;
		return "nil";
    }

	public void renderTo(ObjectCache objectCache,
            ValuesProvider valuesProvider, HttpServletResponse response,
            ViewContext mixInContext, QueryKeyViolations violations) {
		renderToCalled = true;
    }

	public String getViewTitle() {
	    return "Null";
    }
}
