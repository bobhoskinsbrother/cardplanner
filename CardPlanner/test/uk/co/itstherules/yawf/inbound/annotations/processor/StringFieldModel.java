package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class StringFieldModel {
	@QueryKey("bindToMe") private String string;
	private String string2;
	public String getString() { return string; }
	public String getString2() { return string2; }
}