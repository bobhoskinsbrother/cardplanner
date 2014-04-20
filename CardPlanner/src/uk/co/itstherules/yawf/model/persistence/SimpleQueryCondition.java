package uk.co.itstherules.yawf.model.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uk.co.itstherules.yawf.model.persistence.QueryConditions.Operator;

public final class SimpleQueryCondition implements QueryCondition {
	
	private final String name;
	private final Operator operator;
	private final String parameterKey;
    private final Map<String, Object> map;

	SimpleQueryCondition(String name, Operator operator, String parameterKey, Object value) {
		this.name = name;
		this.operator = operator;
        this.parameterKey = parameterKey;
        this.map = new HashMap<String, Object>();
        map.put(parameterKey, value);
	}
	
	@Override
	public String toString() {
        return new StringBuilder(":").append(this.parameterKey).append(" ").append(this.operator.operator).append(" ").append("entity.").append(this.name).toString();
	}

    @Override
    public Map<String, Object> getParameterValues() {
        return Collections.unmodifiableMap(map);
    }

    public String getName() { return name; }

}