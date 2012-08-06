package uk.co.itstherules.cardplanner.model;


public final class PointModel implements Comparable<PointModel> {
	private final double x;
	private final double y;
	private final String legend;

	public PointModel(double x, double y, String legend) {
		this.x = x;
		this.y = y;
		this.legend = legend;
    }

	public double getX() {
    	return x;
    }

	public double getY() {
    	return y;
    }

	public String getLegend() {
    	return legend;
    }

	public int compareTo(PointModel o) {
		if(x>o.x) return 1;
		if(x<o.x) return -1;
		return 0;
    }
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("x:");
		buffer.append(x);
		buffer.append(",y:");
		buffer.append(y);
		buffer.append(",legend:");
		buffer.append(legend);
		return buffer.toString();
	}
}