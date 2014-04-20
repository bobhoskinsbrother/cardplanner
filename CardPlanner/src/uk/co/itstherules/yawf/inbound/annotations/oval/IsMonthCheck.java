package uk.co.itstherules.yawf.inbound.annotations.oval;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

public class IsMonthCheck extends AbstractAnnotationCheck<IsAlphanumeric> {

	private static final long serialVersionUID = 3658934292909987138L;
	public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) {
		if(Integer.class.isInstance(value)) {
			Integer castValue = Integer.class.cast(value);
			return castValue > 0 && castValue < 13;
		}
		return false;
	}
	
}
