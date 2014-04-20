package uk.co.itstherules.yawf.modelview;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.register.Keyed;
import uk.co.itstherules.yawf.view.context.ViewContext;

public interface ModelView extends Keyed {
	
	public String getViewTitle();
	public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) throws IllegalArgumentException;
	public void renderTo(ObjectCache objectCache, ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations) throws IllegalArgumentException;

}
