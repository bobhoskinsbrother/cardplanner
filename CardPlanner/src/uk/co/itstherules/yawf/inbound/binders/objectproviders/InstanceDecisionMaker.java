package uk.co.itstherules.yawf.inbound.binders.objectproviders;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public interface InstanceDecisionMaker {

	boolean shouldInstantiate(ObjectCache objectCache, ValuesProvider provider);
	
}