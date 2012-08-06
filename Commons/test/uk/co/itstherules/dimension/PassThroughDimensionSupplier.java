package uk.co.itstherules.dimension;


public class PassThroughDimensionSupplier implements DimensionSupplier {
	
	private final int width;

	private final int height;

	public PassThroughDimensionSupplier(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
