package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class SignUpTeamToAccept extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		EmptyContext context = new EmptyContext();
		if(mixInContext!=null) {context.putAll(mixInContext);}
		return new MergedTextView("signupteamtoaccept/show.freemarker").asText(context, provider.getApplicationRoot());
    }
	
}