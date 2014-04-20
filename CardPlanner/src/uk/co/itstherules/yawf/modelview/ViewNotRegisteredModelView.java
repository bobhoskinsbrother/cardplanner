package uk.co.itstherules.yawf.modelview;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class ViewNotRegisteredModelView implements ModelView {

	
	@Override public String getKey() {
		return "ViewNotRegisteredView";
	}

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		return "Programmatic error: the view you were looking for has not been registered with the ViewRegister";
    }

	@Override
    public void renderTo(ObjectCache objectCache,
            ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations) {
    }

	@Override
    public String getViewTitle() {
	    return "View Not Registered";
    }
}