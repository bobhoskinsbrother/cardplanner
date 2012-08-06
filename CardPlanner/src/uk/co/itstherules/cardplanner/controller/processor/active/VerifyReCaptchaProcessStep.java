package uk.co.itstherules.cardplanner.controller.processor.active;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.constraint.AssertValidCheck;
import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.captcha.Captcha;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class VerifyReCaptchaProcessStep extends BaseProcessStep<Entity<?>> {
	
	public static final String KEY = "VerifyReCaptcha";
	
    public Entity<?> process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object object = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	Assertion.checkIsInstance(Entity.class, object);
		Entity<?> entity = Entity.class.cast(object);
		if(provider.getBoolean("captcha", Boolean.FALSE)) {
	        String reResponse = provider.getString("recaptcha_response_field");
	        if (!Captcha.isValid(provider)) {
	        	violations.add("captcha", Collections.singletonList(new ConstraintViolation(new AssertValidCheck(), "Captcha is invalid", null, reResponse, null)));
	        }
		}
		if(violations.isRegistered()) { throw new QueryKeyViolationsException(violations); }
	    return entity;
    }

	public String getKey() {
		return KEY;
	}

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(Entity.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(Entity.class); }

	
	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.MODULE_CALL;
    }


}

//@SuppressWarnings("unused") @QueryKey("captcha") @UseBinder(CaptchaBinder.class) @AssertTrue private boolean captcha;
