package uk.co.itstherules.cardplanner.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import uk.co.itstherules.date.DateBuilder;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity
public final class BurnUpModel extends IdentifiableDeleteableModel<BurnUpModel> {

    @ManyToOne @QueryKey("status") private StatusModel status;
    @QueryKey("target") private int target;
    @QueryKey("from") private Date from;
    @QueryKey("to") private Date to;

    public BurnUpModel defaultSetup(ObjectCache objectCache) {
        final long timeInMilliseconds = Calendar.getInstance().getTimeInMillis();
        from = new DateBuilder(timeInMilliseconds).build();
        to = new DateBuilder(timeInMilliseconds).build();
        status = new StatusService().backlog(objectCache);
        target = 0;
        return this;
    }

    public int getTarget() {
        return target;
    }

    public StatusModel getStatus() {
        return status;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}