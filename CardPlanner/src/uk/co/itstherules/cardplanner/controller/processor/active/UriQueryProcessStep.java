package uk.co.itstherules.cardplanner.controller.processor.active;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.UriModel;
import uk.co.itstherules.yawf.model.UriQueryModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public final class UriQueryProcessStep extends BaseProcessStep<Object> {
	
	public static final String KEY = "UriQuery";

    public Object process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object object = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	Assertion.checkIsInstance(String.class, object);
    	String uri = String.class.cast(object);
		return new UriQueryModel(new UriModel(uri)).execute(objectCache);
    }

	public String getKey() {
		return KEY;
	}


	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.MODULE_CALL;
    }

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(String.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(Object.class); }

}