package uk.co.itstherules.cardplanner.controller.processor.active;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.instantiator.Instantiator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public final class MakeNewEntityProcessStep extends BaseProcessStep<Entity<?>> {
	
	public static final String KEY = "MakeNewEntity";

    public Entity<?> process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object classNameObject = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	Assertion.checkIsInstance(String.class, classNameObject);
		String className = String.class.cast(classNameObject);
		Entity<?> instantiated = new Instantiator().<Entity<?>>instantiate(className);
	    instantiated.defaultSetup(objectCache);
	    return instantiated;
    }

	public String getKey() {
		return KEY;
	}

	@Override public ProcessStepType getProcessStepType() { return ProcessStepType.MODULE_CALL; }
	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(String.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(Entity.class); }


}