package uk.co.itstherules.cardplanner.controller.processor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.security.objectcache.GroupModel;

public abstract class BaseSetupAccountProcessStep<O extends Entity<O>> extends BaseProcessStep<O> {
	
	private Set<GroupModel> groups = new HashSet<GroupModel>();

	public abstract O process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations);

	protected PersonModel processPerson(ObjectCache objectCache, ValuesProvider provider) {
	    PersonModel person = new PersonModel().defaultSetup(objectCache);
		QueryKeyViolations violations =  new BasicValuesProviderBinder().bind(provider, person, objectCache);
		if(violations.isRegistered()) {
			throw new QueryKeyViolationsException(violations);
		}

	    String username = person.getUserName();
	    if("".equals(username)) {
	    	person.setUsername(person.getEmail());
	    }
	    String firstName = person.getFirstName();
	    String lastName = person.getLastName();
	    if(!"".equals(firstName) && !"".equals(lastName)){
	    	String initials = new StringBuilder().append(firstName.substring(0, 1)).append(lastName.substring(0, 1)).toString();
	    	person.setInitials(initials);
	    }
	    person.setGroups(groups);
	    person.pending();
	    objectCache.save(person);
	    return person;
    }

	public abstract String getKey();

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(GroupModel.class); }

}
