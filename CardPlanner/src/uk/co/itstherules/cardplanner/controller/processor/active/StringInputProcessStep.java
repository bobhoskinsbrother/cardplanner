package uk.co.itstherules.cardplanner.controller.processor.active;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public final class StringInputProcessStep extends BaseProcessStep<String> {
	
	public static final String KEY = "StringInput";

    public String process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object input = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	String reply = String.class.cast(input);
    	return reply;
    }

	public String getKey() {
		return KEY;
	}

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(String.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(String.class); }

	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.TERMINAL_START;
    }

}