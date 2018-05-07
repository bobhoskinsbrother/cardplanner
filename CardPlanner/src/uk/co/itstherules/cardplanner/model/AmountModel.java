package uk.co.itstherules.cardplanner.model;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AmountModel<T extends AmountModel<T, ET>, ET> extends IdentifiableDeleteableModel<T> {

	@QueryKey("amount") @NotNull private double amount;

	public AmountModel() {
		super();
    }

	public void setAmount(double amount) {
		this.amount = amount;
		this.setSortOrder((int)amount);
	}

	public double getAmount() { return amount; }
	public abstract ET getType();
}