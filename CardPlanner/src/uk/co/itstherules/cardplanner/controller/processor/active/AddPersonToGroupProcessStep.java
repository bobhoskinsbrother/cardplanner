package uk.co.itstherules.cardplanner.controller.processor.active;

import java.text.MessageFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.controller.processor.ProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.security.objectcache.GroupModel;

public final class AddPersonToGroupProcessStep extends BaseProcessStep<PersonModel> {

	public static final String KEY = "AddPersonToGroup";

	public PersonModel process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
		
		Collection<ProcessStep<?>> inputSteps = getInputProcessSteps();
		PersonModel person = null;
		GroupModel group = null;
		
		for (ProcessStep<?> processStep : inputSteps) {
	        boolean set = false;
	        Object reply = processStep.process(objectCache, provider, response, viewFactory, violations);
	        if(PersonModel.class.isInstance(reply)) {
	        	person = PersonModel.class.cast(reply);
		        set = true;
	        }
	        if(GroupModel.class.isInstance(reply)) {
	        	group = GroupModel.class.cast(reply);
		        set = true;
	        }
	        Assertion.checkIsTrue(set, MessageFormat.format("Process {0} was expected to either produce a PersonModel, or GroupModel.  It did neither.  It produced {1}", processStep.getKey(), reply));
        }
		Assertion.checkNotInvisibleObject(person, "PersonModel has not been set in " + getKey());
		Assertion.checkNotInvisibleObject(group, "GroupModel has not been set in " + getKey());
	    person.addGroup(group);
	    return person;
    }

	public String getKey() {
		return KEY;
	}

	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.MODULE_CALL;
    }

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(PersonModel.class, GroupModel.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(PersonModel.class); }

}
