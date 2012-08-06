package uk.co.itstherules.yawf.inbound.annotations.processor;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class BooleanFieldModel {
	@QueryKey("bindToMe") @NotNull private Boolean booleanValue;
	private Boolean booleanValue2;
	public Boolean getBoolean() { return booleanValue; }
	public Boolean getBoolean2() { return booleanValue2; }
}