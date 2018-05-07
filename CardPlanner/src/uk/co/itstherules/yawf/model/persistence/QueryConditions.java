package uk.co.itstherules.yawf.model.persistence;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

public class QueryConditions {
	
	public enum Operator {
		Equals("="), NotEquals("!="), LessThan("<"), MoreThan(">"), Like("like"), MemberOf("MEMBER OF");
		final String operator;

		private Operator(String operator){
			this.operator = operator;
		}
	};
	
	private List<QueryCondition> list;
	private final String joiningOperator;
	private final int incremented;
	
	public QueryConditions(String joiningOperator, int incremented) {
		this.joiningOperator = joiningOperator;
		this.incremented = incremented;
		this.list = new LinkedList<QueryCondition>();
    }

	public QueryConditions(String joiningOperator) {
		this(joiningOperator, 0);
    }
	
	public QueryConditions putAll(Map<String, Object> values) {
		for (String key : values.keySet()) {
	        put(key, values.get(key));
        }
		return this;
	}
	
	public QueryConditions put(String name, Object value) {
		put(name, Operator.Equals, value);
		return this;
	}
	
	public QueryConditions put(String name, Operator operator, Object value) {
		String parameterKeyDecorated = new StringBuilder().append(name.replaceAll("\\.", "_")).append("_").append(this.incremented).append("_").append(list.size()).toString();
		return put(new SimpleQueryCondition(name, operator, parameterKeyDecorated, value));
	}
	
	public QueryConditions between(String name, Date from, Date to) {
		String pre = new StringBuilder("_").append(this.incremented).append("_").append(list.size()).toString();
		return put(new BetweenQueryCondition(name, pre, from, to));
	}

	public QueryConditions compare(String fieldOne, Operator operator, String fieldTwo) {
		return put(new CompareFieldsQueryCondition(fieldOne, operator, fieldTwo));
	}

	private QueryConditions put(QueryCondition queryCondition) {
		this.list.add(queryCondition);
		return this;
	}

	public Query setParameters(Query query) {
		for (QueryCondition condition : this.list) {
            Map<String, Object> parameterValues = condition.getParameterValues();
            for(Map.Entry<String, Object> entry : parameterValues.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
		}
		return query;
    }
	
	@Override public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		Iterator<QueryCondition> iterator = list.iterator();
		while (iterator.hasNext()) {
	        QueryCondition condition = iterator.next();
	        builder.append(condition.toString());
	        if(iterator.hasNext()) {
		        builder.append(" ");
		        builder.append(joiningOperator);
		        builder.append(" ");
	        }
        }
		builder.append(")");
		return builder.toString();
	}
	
	public boolean isEmpty() {
	    return this.list.isEmpty();
    }
}
