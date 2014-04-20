package uk.co.itstherules.yawf.controller.processor;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class StringProcessStep implements ProcessStep<String> {
	
	private final String string;
	
	public StringProcessStep(String string) { this.string = string; }
	
	@Override public String getKey() { return "String"; }
	@Override public String process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) throws QueryKeyViolationsException {
		return string;
	}
	@Override public ProcessStepType getProcessStepType() { return ProcessStepType.TERMINAL_START; }
	@Override public ClassDescriptionModel[] getInputs() { return new ClassDescriptionModel[]{}; }
	@Override public ClassDescriptionModel getOutput() { return new ClassDescriptionModel(String.class); }
	@Override public void addInputStep(ProcessStep<?> step) { }
	@Override public Collection<ProcessStep<?>> getInputProcessSteps() { return new ArrayList<ProcessStep<?>>(); }
	
	
}
