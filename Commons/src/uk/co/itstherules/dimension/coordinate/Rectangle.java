package uk.co.itstherules.dimension.coordinate;

public interface Rectangle {
	
	TopLeft getTopLeft();
	TopRight getTopRight();
	BottomLeft getBottomLeft();
	BottomRight getBottomRight();
	int getHeight();
}
