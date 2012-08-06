package uk.co.itstherules.dimension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DimensionComparatorTest {

	@Test public void testSameWidthAndHeight() {
		DimensionSupplier expected = new PassThroughDimensionSupplier(10,10);
		DimensionSupplier actual = new PassThroughDimensionSupplier(10,10);
		DimensionComparator unit = new DimensionComparator();
		assertTrue("Expected to be the same width and height", unit.same(expected, actual));
	}

	@Test public void testSameWidthAndDiffHeight() {
		DimensionSupplier expected = new PassThroughDimensionSupplier(10,10);
		DimensionSupplier actual = new PassThroughDimensionSupplier(10,30);
		DimensionComparator unit = new DimensionComparator();
		assertFalse("Expected to be the same width and height", unit.same(expected, actual));
	}
	
	@Test public void testDiffWidthAndSameHeight() {
		DimensionSupplier expected = new PassThroughDimensionSupplier(10,10);
		DimensionSupplier actual = new PassThroughDimensionSupplier(30,10);
		DimensionComparator unit = new DimensionComparator();
		assertFalse("Expected to be the same width and height", unit.same(expected, actual));
	}
	
	@Test public void testDiffWidthAndHeight() {
		DimensionSupplier expected = new PassThroughDimensionSupplier(10,10);
		DimensionSupplier actual = new PassThroughDimensionSupplier(30,30);
		DimensionComparator unit = new DimensionComparator();
		assertFalse("Expected to be the same width and height", unit.same(expected, actual));
	}	
}
