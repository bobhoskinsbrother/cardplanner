package uk.co.itstherules.yawf.inbound.annotations.processor;

import net.sf.oval.constraint.Min;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class IntegerFieldModel {
	@QueryKey("bindToMe") @Min(1) private Integer integer;
	private Integer integer2;
	public Integer getInteger() { return integer; }
	public Integer getInteger2() { return integer2; }
}