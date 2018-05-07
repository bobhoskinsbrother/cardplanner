package uk.co.itstherules.yawf.inbound.binders.objectproviders;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class AlwaysTrueDecisionMaker implements InstanceDecisionMaker {

	public boolean shouldInstantiate(ObjectCache objectCache, ValuesProvider provider) { return true; }
	
}