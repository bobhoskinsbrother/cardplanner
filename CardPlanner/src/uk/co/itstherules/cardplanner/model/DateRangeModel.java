package uk.co.itstherules.cardplanner.model;

import java.util.Date;

import javax.persistence.Entity;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;

@Entity  
public final class DateRangeModel extends IdentifiableDeleteableModel<DateRangeModel> {
	
	@QueryKey("fromDate") @NotNull private Date fromDate;
	@QueryKey("toDate") @NotNull private Date toDate;

	public DateRangeModel() {
		super();
		this.fromDate = new Date();
		this.toDate = new Date();
		setTitle("Date Range");
	}
	
	public void setFromDate(Date from) {
    	this.fromDate = from;
    }

	public void setToDate(Date to) {
    	this.toDate = to;
    }
	
	public Date getFromDate() {
    	return fromDate;
    }
	
	public Date getToDate() {
    	return toDate;
    }

	public boolean isInRange(Date date) {
		return date.after(fromDate) && date.before(toDate);
	}

	public long getRangeAmount() {
		long milliseconds1 = fromDate.getTime();
	    long milliseconds2 = toDate.getTime();
	    long diff = milliseconds2 - milliseconds1;
	    return (diff / (24 * 60 * 60 * 1000));
	}
	
	@Override
	public String toString() {
		return new Json<Object>().serialize(this);
	}

    public DateRangeModel defaultSetup(ObjectCache objectCache) { return this; }


}
