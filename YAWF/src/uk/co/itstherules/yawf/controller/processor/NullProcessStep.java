package uk.co.itstherules.yawf.controller.processor;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.NullEntity;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class NullProcessStep implements ProcessStep<NullEntity> {
	
	@Override public String getKey() { return "Null"; }
	@Override public NullEntity process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) throws QueryKeyViolationsException {
		return new NullEntity();
	}
	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.TERMINAL_END;
    }

	@Override public ClassDescriptionModel[] getInputs() { return new ClassDescriptionModel[]{}; }
	@Override public ClassDescriptionModel getOutput() { return new ClassDescriptionModel(NullEntity.class); }
	@Override public void addInputStep(ProcessStep<?> step) { }
	@Override public Collection<ProcessStep<?>> getInputProcessSteps() {
	    return Collections.<ProcessStep<?>>singletonList(this);
    }
	
	
}
