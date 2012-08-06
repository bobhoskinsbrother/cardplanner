package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class LoginShow extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		View view = new MergedTextView("login/show.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		if(mixInContext!=null) {context.putAll(mixInContext);}
		if("error.xhtml".equals(provider.getTitle())) {
			context.put("error", "The credentials you have entered are incorrect");
		} else {
			context.put("error", "");
		}
		return view.asText(context, root);
    }
	
}