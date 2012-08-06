package uk.co.itstherules.yawf.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class RouteModel extends IdentifiableDeleteableModel<RouteModel> {
	
	@QueryKey("routeController") private String routeController = "";
	@QueryKey("routeAction") private String routeAction = "Show";
	@QueryKey("routeIdentity") private String routeIdentity = "0";

	@OneToOne @QueryKey(value="routeProcessStep", cache=CacheInstruction.FromCache) private ProcessStepReferenceModel routeProcessStep;
	
	public String getController() { return routeController; }
	public String getAction() { return routeAction; }
	public String getRouteIdentity() { return routeIdentity; }
	public String getTitle() { return title; }
	public ProcessStepReferenceModel getRouteProcessStep() { return routeProcessStep; }

    @Override public RouteModel defaultSetup(ObjectCache objectCache) { return this; }
}
