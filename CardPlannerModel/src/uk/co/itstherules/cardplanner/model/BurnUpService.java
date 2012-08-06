package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;

import java.util.Date;
import java.util.Set;

public final class BurnUpService {

    public BurnUpLineModel betweenDatesForStatus(ObjectCache objectCache, Date from, Date to, StatusModel status) {
        QueryConditions andConditions = new QueryConditions("AND").between("date", from, to).compare("fromStatus.identity", QueryConditions.Operator.NotEquals, "toStatus.identity");
        String statusIdentity = status.getIdentity();
        QueryConditions orConditions  = new QueryConditions("OR").put("fromStatus.identity", statusIdentity).put("toStatus.identity", statusIdentity);
        Set<LogModel> logs = objectCache.all(LogModel.class, andConditions, orConditions);
        return new BurnUpLineModel(status, from, to, logs);
    }

}
