package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.sf.oval.ConstraintViolation;

public class QueryKeyViolations {
	
	private final HashMap<String, List<ConstraintViolation>> map;
	private boolean isRegistered = false;

	public QueryKeyViolations() {
		this.map = new HashMap<String, List<ConstraintViolation>>();
    }
	
	public void add(String queryKey, List<ConstraintViolation> violations) {
		if(violations.size() > 0) {
			this.isRegistered = true;
			List<ConstraintViolation> queryKeyViolations = getViolations(queryKey);
			queryKeyViolations.addAll(violations);
			this.map.put(queryKey, queryKeyViolations); 
		}
    }
	
	public List<ConstraintViolation> getViolations(String queryKey) {
		List<ConstraintViolation> reply = this.map.get(queryKey);
		if(reply==null) { return new ArrayList<ConstraintViolation>(); }
		return reply;
    }

	public List<String> getViolationMessages(String queryKey) {
		List<String> messages = new ArrayList<String>();
		List<ConstraintViolation> violations = getViolations(queryKey);
		for (ConstraintViolation violation : violations) {
	        messages.add(violation.getMessage());
        }
		return messages;
    }
	
	public boolean isViolated(String queryKey) {
		List<ConstraintViolation> violations = getViolations(queryKey);
		return violations.size() > 0;
    }

	public boolean isRegistered() {
		return isRegistered ;
    }

	public void mergeWith(QueryKeyViolations violations) {
		map.putAll(violations.map);
    }
	
	@Override
	public String toString() {
		String separator = System.getProperty("line.separator");
		StringBuffer buffer = new StringBuffer();
		for (Entry<String, List<ConstraintViolation>> violation : map.entrySet()) {
	        buffer.append(violation.getKey());
	        buffer.append(" has the following violations:");
			buffer.append(separator);
	        List<ConstraintViolation> value = violation.getValue();
	        for (ConstraintViolation constraintViolation : value) {
	        	buffer.append(constraintViolation.getCheckName());
				buffer.append(separator);
            }
			buffer.append(separator);
        }
		return buffer.toString();
	}
	
}
