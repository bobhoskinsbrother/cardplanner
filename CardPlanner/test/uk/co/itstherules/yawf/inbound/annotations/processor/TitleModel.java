package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class TitleModel {
	@QueryKey("title") private String title;
	public String getTitle() { return title; }
}