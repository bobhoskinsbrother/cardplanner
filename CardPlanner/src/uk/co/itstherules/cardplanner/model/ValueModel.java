package uk.co.itstherules.cardplanner.model;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public final class ValueModel extends AmountModel<ValueModel, ValueTypeModel> {

	@ManyToOne @QueryKey(value="type", cache=CacheInstruction.FromCache) @NotNull private ValueTypeModel type;

	public ValueModel() {
		super();
	}
	
	public ValueModel defaultSetup(ObjectCache objectCache) {
        setAmount(0.0);
        setTitle("Default");
        this.type = SpecialInstances.<ValueTypeModel>retrieve(objectCache, Identities.CURRENCY_VALUE_TYPE);
	    return this;
	}
	@Override
    public ValueTypeModel getType() { return type; }
	public void setType(ValueTypeModel valueType) {type = valueType; }

}
