package uk.co.itstherules.cardplanner.model;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.type.TypeModel;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;


public abstract class AmountModel<T extends AmountModel<T>> extends IdentifiableDeleteableModel<T> {

	@QueryKey("amount") @NotNull private double amount;

	public AmountModel() {
		super();
		setAmount(0.0);
		setTitle("Default");
    }
	
	public void setAmount(double amount) {
		this.amount = amount;
		this.setSortOrder((int)amount);
	}

	public double getAmount() { return amount; }
	public abstract TypeModel getType();
}