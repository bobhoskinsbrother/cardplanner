package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.List;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class ListOfStringsFieldModel {
	@QueryKey("bindToMe") @NotNull private List<String> strings;
	private List<String> strings2;
	public List<String> getStrings() { return strings; }
	public List<String> getStrings2() { return strings2; }
}