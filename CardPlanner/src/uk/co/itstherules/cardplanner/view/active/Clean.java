package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.context.JavascriptAndCssContext;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class Clean extends BaseModelView {
	

	@Override public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		ViewContext context = new JavascriptAndCssContext(valuesProvider.getApplicationRoot());
		context.put("feedMe", false);
		if(mixInContext!= null) { context.putAll(mixInContext); }
	    return new MergedTextView("clean.freemarker").asText(context, valuesProvider.getApplicationRoot());
    }
	
}