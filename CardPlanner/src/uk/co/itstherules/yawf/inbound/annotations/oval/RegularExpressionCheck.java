package uk.co.itstherules.yawf.inbound.annotations.oval;

import java.util.regex.Pattern;

public class RegularExpressionCheck {
	
	public static boolean isSatisfied(String regularExpression, Object value) {
		if (value == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regularExpression);
		return pattern.matcher(value.toString()).matches();
	}
}
