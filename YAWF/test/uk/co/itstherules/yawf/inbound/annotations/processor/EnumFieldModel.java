package uk.co.itstherules.yawf.inbound.annotations.processor;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class EnumFieldModel {
	@QueryKey("bindToMe") @NotNull private TestEnum testEnum;
	private  TestEnum testEnum2;
	public  TestEnum getEnum() { return testEnum; }
	public  TestEnum getEnum2() { return testEnum2; }
}