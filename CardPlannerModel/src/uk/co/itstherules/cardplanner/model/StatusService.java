package uk.co.itstherules.cardplanner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.model.comparator.OrderComparator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.model.persistence.QueryConditions.Operator;

public final class StatusService {
	
	public StatusModel finalStatus(ObjectCache objectCache) {
		Set<StatusModel> statuses = objectCache.all(StatusModel.class);
		StatusModel theBacklog = SpecialInstances.retrieve(objectCache, Identities.THE_BACKLOG);
		return finalStatus(statuses, theBacklog);
	}
	
	public Set<StatusModel> everythingButTheBacklog(ObjectCache objectCache) {
		Set<StatusModel> statuses = objectCache.all(StatusModel.class, new QueryConditions("AND").put("identity", Operator.NotEquals, Identities.THE_BACKLOG.getIdentity()));
		return statuses;
    }
	
	public StatusModel finalStatus(Set<StatusModel> statuses, StatusModel theBacklog) {
		if(statuses.isEmpty() || statuses.size() < 2) {
			throw new IllegalArgumentException("Statuses need at least 2 in the list");
		}
		List<StatusModel> statusesList = new ArrayList<StatusModel>(statuses);
		Collections.sort(statusesList, new OrderComparator());
		StatusModel status = statusesList.get(statusesList.size()-1);
		if(theBacklog.getIdentity().equals(status.getIdentity())){
			status = statusesList.get(statusesList.size()-2);
		}
		return status;
	}
	
	public Set<StatusModel> otherStatuses(Set<StatusModel> statuses, StatusModel theBacklog) {
		if(statuses.isEmpty() || statuses.size() < 2) {
			throw new IllegalArgumentException("Statuses need at least 2 in the list");
		}
		StatusModel finalStatus = finalStatus(statuses, theBacklog);
		statuses.remove(finalStatus);
		statuses.remove(theBacklog);
		return statuses;
	}

    public StatusModel backlog(ObjectCache objectCache) {
        return objectCache.retrieveByIdentity(StatusModel.class, Identities.THE_BACKLOG.getIdentity());
    }
}
