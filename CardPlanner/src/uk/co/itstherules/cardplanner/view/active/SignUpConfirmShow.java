package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class SignUpConfirmShow extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		ViewContext context = new EmptyContext();
		if(mixInContext!=null) {context.putAll(mixInContext);}
		PersonModel person = objectCache.retrieveByIdentity(PersonModel.class, provider.getIdentity(), ObjectState.Active);
		
		context.put("person", person);
		context.put("base", provider.getBase());
		context.put("defaultHomePage", provider.getString("defaultHomePage")); 

		return new MergedTextView("signupconfirm/show.freemarker").asText(context, provider.getApplicationRoot());
    }
	
}