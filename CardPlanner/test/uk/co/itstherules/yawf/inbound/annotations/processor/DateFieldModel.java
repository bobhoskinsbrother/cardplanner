package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.Date;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class DateFieldModel {
	@QueryKey("bindToMe") @NotNull private Date dateValue;
	private Date dateValue2;
	public Date getDate() { return dateValue; }
	public Date getDate2() { return dateValue2; }
}