package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.type.TypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


@Entity
public final class ValueModel extends AmountModel<ValueModel> {

	@ManyToOne @QueryKey(value="type", cache=CacheInstruction.FromCache) @NotNull private ValueTypeModel type;

	public ValueModel() {
		super();
	}
	
	public ValueModel defaultSetup(ObjectCache objectCache) {
		this.type = SpecialInstances.<ValueTypeModel>retrieve(objectCache, Identities.CURRENCY_VALUE_TYPE);
	    return this;
	}
	@Override
    public TypeModel getType() { return type; }
	public void setType(ValueTypeModel valueType) {type = valueType; }

}
