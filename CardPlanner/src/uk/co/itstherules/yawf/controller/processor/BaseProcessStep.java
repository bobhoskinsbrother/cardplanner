package uk.co.itstherules.yawf.controller.processor;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public abstract class BaseProcessStep<O> implements ProcessStep<O> {
	
	private final List<ProcessStep<?>> steps = Collections.synchronizedList(new LinkedList<ProcessStep<?>>());

	protected Object processInboundStep(int index, ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	List<ProcessStep<?>> inputSteps = getInputProcessSteps();
    	if(inputSteps.size() < index) {
    		throw new IllegalArgumentException(MessageFormat.format("There is no Input Step set at index {0} for ProcessStep {1}", index, getKey()));
    	}
		return inputSteps.get(index).process(objectCache, provider, response, viewFactory, violations);
	}
	
	protected Object processFirstInboundStep(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	return processInboundStep(0, objectCache, provider, response, viewFactory, violations);
	}

	@Override public List<ProcessStep<?>> getInputProcessSteps() {
		return Collections.unmodifiableList(steps);
	}
	
	@Override public void addInputStep(ProcessStep<?> step) {
		this.steps.add(step);
	}

}