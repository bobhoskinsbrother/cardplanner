package uk.co.itstherules.yawf.model.persistence;

import java.util.HashMap;
import java.util.Map;

public final class CompareFieldsQueryCondition implements QueryCondition {

	private final String fieldOne;
	private final String fieldTwo;
    private final QueryConditions.Operator operator;


	CompareFieldsQueryCondition(String fieldOne, QueryConditions.Operator operator, String fieldTwo) {
        this.fieldOne = fieldOne;
        this.operator = operator;
        this.fieldTwo = fieldTwo;
    }
	
	@Override
	public String toString() {
        return new StringBuilder("entity.").append(this.fieldOne).append(" ").append(operator.operator).append(" entity.").append(fieldTwo).toString();
	}

    @Override
    public Map<String, Object> getParameterValues() {
        return new HashMap<String, Object>();
    }

}