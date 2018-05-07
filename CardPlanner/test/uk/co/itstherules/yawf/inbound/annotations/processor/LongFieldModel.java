package uk.co.itstherules.yawf.inbound.annotations.processor;

import net.sf.oval.constraint.Min;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class LongFieldModel {
	@QueryKey("bindToMe") @Min(1) private Long longValue;
	private Long longValue2;
	public Long getLong() { return longValue; }
	public Long getLong2() { return longValue2; }
}