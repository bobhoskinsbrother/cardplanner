package uk.co.itstherules.yawf.model;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.model.persistence.FieldValue;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.model.persistence.QueryConditions.Operator;

public final class UriQueryModel implements UriExecutor {
	
	@SuppressWarnings("rawtypes")
	private final Class<? extends IdentityDeleteable> classToFind;
	private final TypeQuery typeOfQuery;
	private final Map<String, Object> queryString;
	private final String identity;
	private String modelName;
	
	private enum TypeQuery {
		all, count, retrievebyidentity, retrieve, first, like
	}

    public UriQueryModel(UriModel uriModel) {
		Assertion.checkIsSame(UriModel.Scheme.query, uriModel.getScheme(), "UriQueryModel can only handle a query uri");
		this.modelName = uriModel.getPath(1);
		Assertion.checkNotNull(this.modelName);
		try {
			this.classToFind = Class.forName(this.modelName).asSubclass(IdentityDeleteable.class);
	        if(!Entity.class.isAssignableFrom(this.classToFind)) {
		        throw new IllegalArgumentException("Classes need to extend Entity");
	        }
        } catch (ClassNotFoundException e) {
	        throw new IllegalArgumentException(e);
        }
        this.typeOfQuery = TypeQuery.valueOf(uriModel.getPath(2).toLowerCase());
        this.queryString = uriModel.getQueryString();
        this.identity = uriModel.getPath(3);
    }

	public Object execute(final ObjectCache objectCache) {
		switch (this.typeOfQuery) {
	        case count:
	        	if(queryString.keySet().size()==0) {
	        		return objectCache.count(this.classToFind);
	        	}
        		return objectCache.count(this.classToFind, this.queryString);
	        case retrievebyidentity:
	        	return objectCache.retrieveByIdentity(this.classToFind, this.identity);
	        case retrieve:
	        	FieldValue singleValue = singleValue();
	        	return objectCache.retrieve(this.classToFind, singleValue.getField(), singleValue.getValue());
	        case like:
	        	singleValue = singleValue();
	        	return objectCache.all(this.classToFind, new QueryConditions("AND").put(singleValue.getField(), Operator.Like, MessageFormat.format("%{0}%", singleValue.getValue())));
	        case first:
	        	return objectCache.first(this.classToFind);
	        case all:
	        	if(queryString.keySet().size()==0) {
	        		return objectCache.all(this.classToFind);
	        	}
	        	
	        default:
	        	return objectCache.all(this.classToFind, new QueryConditions("AND").putAll(this.queryString));
        }
    }
	
	private FieldValue singleValue() {
		Set<String> keySet = queryString.keySet();
		Assertion.checkIsSize(1, keySet);
		String field = keySet.iterator().next();
		Object value = queryString.get(field);
		return new FieldValue(field, value);
		
	}
	
}
