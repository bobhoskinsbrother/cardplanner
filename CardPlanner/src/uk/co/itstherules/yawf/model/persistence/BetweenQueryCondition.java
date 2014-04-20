package uk.co.itstherules.yawf.model.persistence;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class BetweenQueryCondition implements QueryCondition {

	private final String name;
    private String pre;
    private final Map<String, Object> map;


	BetweenQueryCondition(String name, String pre, Date from, Date to) {
		this.name = name;
        this.pre = pre;
        this.map = new HashMap<String, Object>();
        map.put(pre+"from", from);
        map.put(pre+"to", to);
	}
	
	@Override
	public String toString() {
        return new StringBuilder("entity.").append(this.name).append(" BETWEEN :").append(pre).append("from and :").append(pre).append("to").toString();
	}

    @Override
    public Map<String, Object> getParameterValues() {
        return Collections.unmodifiableMap(map);
    }

    public String getName() { return name; }

}