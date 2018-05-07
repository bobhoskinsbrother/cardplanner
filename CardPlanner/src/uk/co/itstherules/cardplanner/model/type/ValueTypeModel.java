package uk.co.itstherules.cardplanner.model.type;

import javax.persistence.Entity;

import net.sf.oval.constraint.NotNegative;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class ValueTypeModel extends IdentifiableDeleteableModel<ValueTypeModel> implements TypeModel {

	@QueryKey("rate") @NotNegative private double rate;

	public ValueTypeModel() {
		super();
		this.rate = 1.0;
	}
	
	public double getRate() {
	    return rate;
    }

    public double asRate(double amount) {
		try {
			return (amount * getRate());
		} catch (NumberFormatException e) {
			return 0;
		}
    }

    public void setRate(double rate) {
	    this.rate = rate;
    }
    
	@Override
	public String toString() {
		StringBuffer buffer =new StringBuffer();
		buffer.append(getTitle());
		buffer.append(", ");
		buffer.append(rate);
		return buffer.toString();
	}

    public ValueTypeModel defaultSetup(ObjectCache objectCache) { return this; }

}
