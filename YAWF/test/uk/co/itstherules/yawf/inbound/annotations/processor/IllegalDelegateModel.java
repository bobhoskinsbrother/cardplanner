package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class IllegalDelegateModel  {
	
	@SuppressWarnings("unused")
	@QueryKey(value="identity") private String identity;
	@SuppressWarnings("unused")
	@QueryKey(value="stringValue") private String stringValue;
	
	
}