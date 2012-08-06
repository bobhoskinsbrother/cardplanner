package uk.co.itstherules.yawf.controller.processor;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.register.Keyed;

public interface ProcessStep<O> extends Keyed {
	
	public enum ProcessStepType { TERMINAL_START, MODULE_CALL, TERMINAL_END }
	
	O process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) throws QueryKeyViolationsException;
	Collection<ProcessStep<?>> getInputProcessSteps();
	void addInputStep(ProcessStep<?> step);
	
	ClassDescriptionModel[] getInputs();
	ClassDescriptionModel getOutput();
	ProcessStepType getProcessStepType();
	
}
