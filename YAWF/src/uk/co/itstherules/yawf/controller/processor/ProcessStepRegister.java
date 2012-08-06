package uk.co.itstherules.yawf.controller.processor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.model.instantiator.Instantiator;
import uk.co.itstherules.yawf.register.NotRegistered;
import uk.co.itstherules.yawf.register.PackagedClassesAssignableFrom;
import uk.co.itstherules.yawf.register.Register;

public class ProcessStepRegister {
	
	private static ProcessStepRegister INSTANCE;
	
    @SuppressWarnings("rawtypes")
	private final Register<ProcessStep> delegate;
	
    @SuppressWarnings("rawtypes")
	private ProcessStepRegister(final List<String> packages) {
		this.delegate = new PackagedClassesAssignableFrom<ProcessStep>(null).collect(ProcessStep.class, packages);
	}

	public static void init(List<String> list) {
		if(INSTANCE != null) { throw new IllegalStateException("Init can only be called once"); }
		INSTANCE = new ProcessStepRegister(list);
	}
	
	public static ProcessStep<?> createStepFor(String key) {
		Assertion.checkNotNull(INSTANCE);
		try {
			return new Instantiator().instantiate(INSTANCE.get(key).getClass());
		} catch (Exception e) {
			return new NullProcessStep();
		}
	}
	
	public static ProcessStep<?> getStep(String key) {
		return createStepFor(key);
	}
	
	public static Set<String> availableKeys() {
		Assertion.checkNotNull(INSTANCE);
		return INSTANCE.available();
	}
	
	public static Set<ProcessStep<?>> availableSteps() {
		Assertion.checkNotNull(INSTANCE);
		Set<String> available = INSTANCE.available();
		Set<ProcessStep<?>> steps = new LinkedHashSet<ProcessStep<?>>();
		for (String key : available) {
	        steps.add(getStep(key));
        }
		return steps;
	}
	
	private ProcessStep<?> get(String key) { 
		try {
			return this.delegate.get(key); 
		} catch (NotRegistered e) {
			return new NullProcessStep();
		}
	}
	
	private Set<String> available() {
		return this.delegate.available();
    }

}
