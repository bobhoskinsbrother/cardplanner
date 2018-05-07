package uk.co.itstherules.dimension.coordinate;

public class CoordinateTranslator {

	private final int height;

	public CoordinateTranslator(int height) {
		this.height = height;
	}

	public Rectangle to00AtTopLeft(Rectangle rectangle) {
		TopLeft topLeft = rectangle.getTopLeft();
		BottomRight bottomRight = rectangle.getBottomRight();
		
		TopLeft newTopLeft = new TopLeft(topLeft.getX(), Math.abs(height - topLeft.getY() - rectangle.getHeight()));
		BottomRight newBottomRight = new BottomRight(bottomRight.getX(), Math.abs(height - bottomRight.getY() + rectangle.getHeight()));
	
		return new SimpleRectangle(newTopLeft, newBottomRight);
	}

	public Rectangle to00AtBottomLeft(Rectangle rectangle) {
		TopLeft topLeft = rectangle.getTopLeft();
		BottomRight bottomRight = rectangle.getBottomRight();
		
		BottomLeft newBottomLeft = new BottomLeft(topLeft.getX(), Math.abs(height - bottomRight.getY()));
		TopRight newTopRight = new TopRight(bottomRight.getX(), Math.abs(height - topLeft.getY()));
	
		return new SimpleRectangle(newBottomLeft, newTopRight);
	}

}
