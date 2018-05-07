package uk.co.itstherules.yawf.inbound.annotations.processor;

public class QueryKeyViolationsException extends RuntimeException {

	private static final long serialVersionUID = -1256970782441943885L;
	private final QueryKeyViolations violations;
	
	public QueryKeyViolationsException(QueryKeyViolations violations) {
		this.violations = violations;
    }
	
	public QueryKeyViolations getViolations() { return violations; }
}
