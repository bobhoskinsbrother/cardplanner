package uk.co.itstherules.yawf.modelview;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class EmptyModelView extends BaseModelView {

	
	@Override public String getKey() {
		return "EmptyModelView";
	}

	@Override public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		return "";
    }

	@Override public String getViewTitle() { return ""; }
}