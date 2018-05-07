package uk.co.itstherules.yawf.inbound.annotations.processor;

import net.sf.oval.constraint.Min;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class DoubleFieldModel {
	@QueryKey("bindToMe") @Min(1.0) private Double doubleValue;
	private Double doubleValue2;
	public Double getDouble() { return doubleValue; }
	public Double getDouble2() { return doubleValue2; }
}