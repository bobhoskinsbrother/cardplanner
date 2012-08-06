package uk.co.itstherules.dimension.coordinate;

public class SimpleRectangle implements Rectangle {

	private final BottomLeft bottomLeft;
	private final TopRight topRight;
	private final TopLeft topLeft;
	private final BottomRight bottomRight;

	public SimpleRectangle(BottomLeft bottomLeft, TopRight topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
		this.bottomRight = new BottomRight(topRight.getX(), bottomLeft.getY());
		this.topLeft = new TopLeft(bottomLeft.getX(), topRight.getY());
	}

	public SimpleRectangle(TopLeft topLeft, BottomRight bottomRight) {
		this.bottomRight = bottomRight;
		this.topLeft = topLeft;
		this.bottomLeft = new BottomLeft(topLeft.getX(), bottomRight.getY());
		this.topRight = new TopRight(bottomRight.getX(), topLeft.getY());
	}

	public SimpleRectangle(int x1, int y1, int x2, int y2) {
		this(new TopLeft(x1, y1), new BottomRight(x2, y2));
	}

	public BottomLeft getBottomLeft() {
		return this.bottomLeft;
	}

	public BottomRight getBottomRight() {
		return this.bottomRight;
	}

	public TopLeft getTopLeft() {
		return this.topLeft;
	}

	public TopRight getTopRight() {
		return this.topRight;
	}

	public int getHeight() {
		return Math.abs(this.bottomLeft.getY() - this.topLeft.getY());
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Rectangle: [");
		buffer.append(topLeft.toString());
		buffer.append(",");
		buffer.append(topRight.toString());
		buffer.append(",");
		buffer.append(bottomRight.toString());
		buffer.append(",");
		buffer.append(bottomLeft.toString());
		buffer.append("]");
		return buffer.toString();
	}

}
