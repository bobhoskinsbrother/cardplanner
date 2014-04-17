package uk.co.itstherules.yawf.controller.processor;

import uk.co.itstherules.yawf.inbound.ContextValuesProvider;
import uk.co.itstherules.yawf.inbound.DefaultValuesProvider;
import uk.co.itstherules.yawf.model.instantiator.Instantiator;
import uk.co.itstherules.yawf.register.NotRegistered;
import uk.co.itstherules.yawf.register.PackagedClassesAssignableFrom;
import uk.co.itstherules.yawf.register.Register;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProcessStepRegisterListener implements ServletContextListener {
	
    @SuppressWarnings("rawtypes")
	private static Register<ProcessStep> DELEGATE;

    @Override public void contextInitialized(ServletContextEvent event) {
		if(DELEGATE != null) { throw new IllegalStateException("Init can only be called once"); }
        ContextValuesProvider provider = new ContextValuesProvider(event.getServletContext(), new DefaultValuesProvider());
		DELEGATE = new PackagedClassesAssignableFrom<ProcessStep>(null).collect(ProcessStep.class, provider.getList("processRoot"));
    }

    @Override public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

	public static ProcessStep<?> createStepFor(String key) {
		try {
			return new Instantiator().instantiate(get(key).getClass());
		} catch (Exception e) {
			return new NullProcessStep();
		}
	}
	
	public static ProcessStep<?> getStep(String key) {
		return createStepFor(key);
	}
	
	public static Set<String> availableKeys() {
		return available();
	}
	
	public static Set<ProcessStep<?>> availableSteps() {
		Set<String> available = available();
		Set<ProcessStep<?>> steps = new LinkedHashSet<ProcessStep<?>>();
		for (String key : available) {
	        steps.add(getStep(key));
        }
		return steps;
	}
	
	private static ProcessStep<?> get(String key) {
		try {
			return DELEGATE.get(key);
		} catch (NotRegistered e) {
			return new NullProcessStep();
		}
	}
	
	private static Set<String> available() {
		return DELEGATE.available();
    }
}
