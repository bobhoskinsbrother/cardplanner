package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.Map;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class MapOfStringsFieldModel {
	@QueryKey("bindToMe") @NotNull private Map<String, String> strings;
	private  Map<String, String> strings2;
	public  Map<String, String> getStrings() { return strings; }
	public  Map<String, String> getStrings2() { return strings2; }
}