package uk.co.itstherules.dimension;

public class DimensionComparator {

	public boolean same(DimensionSupplier expected, DimensionSupplier actual) {
		if(expected.getWidth() != actual.getWidth()){
			return false;
		}
		if(expected.getHeight() != actual.getHeight()){
			return false;
		}
		return true;
	}

}
