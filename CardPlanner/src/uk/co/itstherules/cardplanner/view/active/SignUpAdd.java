package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.EnumArrayToEntityListConverter;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.captcha.Captcha;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class SignUpAdd extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		PersonModel object = new PersonModel().defaultSetup(objectCache);
		new BasicValuesProviderBinder().bind(provider, object, objectCache);
		ViewContext context = new ChangeContext(provider.getController(), "Add", violations, object);
		if(mixInContext!=null) {context.putAll(mixInContext);}
		context.put("genders", new EnumArrayToEntityListConverter().convert(PersonModel.Gender.values()));
		context.put("action", "Create");
		context.put("captcha", provider.getBoolean("captcha", Boolean.FALSE));
	    context.put("captchaHtml", Captcha.getHtml(provider));
		return new MergedTextView("signup/add.freemarker").asText(context, provider.getApplicationRoot());
    }
	
	@Override
	public String getViewTitle() {
	    return "Sign Up";
	}
	
}