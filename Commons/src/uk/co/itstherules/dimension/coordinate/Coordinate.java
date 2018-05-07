package uk.co.itstherules.dimension.coordinate;

public class Coordinate {

	private final int x;
	private final int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(this.getClass().getSimpleName());
		buffer.append(":");
		buffer.append(x);
		buffer.append(",");
		buffer.append(y);
		buffer.append("]");
		return buffer.toString();
	}
	
}
