package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.TypeModel;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


@Entity 
public final class EffortModel extends AmountModel<EffortModel> {

	@ManyToOne @QueryKey(value="type", cache=CacheInstruction.FromCache) @NotNull private EffortTypeModel type;

	public EffortModel() {
		super();
	}
	
	public EffortModel defaultSetup(ObjectCache objectCache) {
		this.type = SpecialInstances.<EffortTypeModel>retrieve(objectCache, Identities.IDEAL_DAY_EFFORT_TYPE);
	    return this;
	}

	@Override
    public TypeModel getType() { return type; }
	public void setType(EffortTypeModel type) { this.type = type; }
}