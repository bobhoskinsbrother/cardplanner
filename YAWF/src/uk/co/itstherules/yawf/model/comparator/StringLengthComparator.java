package uk.co.itstherules.yawf.model.comparator;

import java.util.Comparator;

public class StringLengthComparator implements Comparator<String> {
	
	@Override public int compare(String left, String right) {
		Integer leftLength = Integer.valueOf(left.length());
		Integer rightLength = Integer.valueOf(right.length());
		if(leftLength.equals(rightLength)) {
			return left.compareTo(right);
		}
		return leftLength.compareTo(rightLength);
	}
	
}
