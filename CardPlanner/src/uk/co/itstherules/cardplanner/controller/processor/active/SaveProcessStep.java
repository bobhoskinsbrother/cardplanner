package uk.co.itstherules.cardplanner.controller.processor.active;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public final class SaveProcessStep extends BaseProcessStep<Entity<?>> {

	public static final String KEY = "Save";

	public Entity<?> process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object object = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	Assertion.checkIsInstance(Entity.class, object);
    	Entity<?> entity = Entity.class.cast(object);
    	objectCache.save(entity);
	    return entity;
    }

	public String getKey() {
		return KEY;
	}
	
	@Override public ProcessStepType getProcessStepType() { return ProcessStepType.MODULE_CALL; }
	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(Entity.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(Entity.class); }

}
