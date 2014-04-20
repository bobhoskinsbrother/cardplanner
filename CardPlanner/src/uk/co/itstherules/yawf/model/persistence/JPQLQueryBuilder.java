package uk.co.itstherules.yawf.model.persistence;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.comparator.OrderComparator;

public class JPQLQueryBuilder {
	
	@SuppressWarnings("unchecked")
    public <T extends IdentityDeleteable<?>> Set<T> select(EntityManager entityManager, Class<T> type, QueryConditions andConditions, QueryConditions orConditions, QueryConditions states){
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT entity FROM ").append(type.getName()).append(" entity WHERE ");
		boolean previousEntered = false;
		if(!andConditions.isEmpty()) {
			builder.append(andConditions.toString());
			previousEntered = true;
		}
		if(!orConditions.isEmpty()) {
			if(previousEntered) builder.append(" AND ");
			builder.append(orConditions.toString());
			previousEntered = true;
		}
		if(!states.isEmpty()) {
			if(previousEntered) builder.append(" AND ");
			builder.append(states.toString());
		}
		
		Query query = entityManager.createQuery(builder.toString());
		query = andConditions.setParameters(query);
		query = orConditions.setParameters(query);
		query = states.setParameters(query);
		List<T> resultList = query.getResultList();
		Collections.sort(resultList, new OrderComparator());
		return new LinkedHashSet<T>(resultList);
	}
}