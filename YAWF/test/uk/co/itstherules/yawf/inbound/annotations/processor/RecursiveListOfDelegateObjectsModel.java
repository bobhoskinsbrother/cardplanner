package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.List;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class RecursiveListOfDelegateObjectsModel {

	@QueryKey("title") @NotNull private String title;
	@QueryKey("rifle") @NotNull private String rifle;
	@QueryKey("delegates") @NotNull private List<RecursiveListOfDelegateObjectsModel> delegates;
	public List<RecursiveListOfDelegateObjectsModel> getDelegates() { return delegates; }
	
	public String getTitle() { return title; }
	public String getRifle() { return rifle; }
	
}