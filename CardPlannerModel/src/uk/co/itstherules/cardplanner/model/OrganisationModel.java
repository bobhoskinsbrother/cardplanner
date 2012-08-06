package uk.co.itstherules.cardplanner.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity  
public final class OrganisationModel extends IdentifiableDeleteableModel<OrganisationModel> {

	public enum OrganisationType {
		Academic, Committee, Community, Company, Government, NonProfit, Political, Project, Society, Team, WorkStream
	}

	@ElementCollection(targetClass=OrganisationType.class) @QueryKey("organisationTypes") private Set<OrganisationType> organisationTypes;
	
	public OrganisationModel() { 
		super(); 
	}
	
	public OrganisationModel defaultSetup(ObjectCache objectCache) {
		this.organisationTypes = new LinkedHashSet<OrganisationType>();
	    return this;
	}
	
	public Set<OrganisationType> getOrganisationTypes() {
	    return organisationTypes;
    }

}
