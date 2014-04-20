package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.List;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class ListOfDelegateObjectsModel {
	@QueryKey("bindToMe") @NotNull private List<TitleModel> titles;
	public List<TitleModel> getTitles() { return titles; }
	
}