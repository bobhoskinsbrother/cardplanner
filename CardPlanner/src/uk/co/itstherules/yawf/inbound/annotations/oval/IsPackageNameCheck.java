package uk.co.itstherules.yawf.inbound.annotations.oval;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

public class IsPackageNameCheck extends AbstractAnnotationCheck<IsPackageName> {

	private static final long serialVersionUID = 3658934292909987138L;
	public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) {
		return RegularExpressionCheck.isSatisfied("((\\p{Alnum}|_)+(\\.(\\p{Alnum}|_)+)*(\\.\\*)?)|\\*", value);
	}
	
}
