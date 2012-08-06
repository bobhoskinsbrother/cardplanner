package uk.co.itstherules.cardplanner.model;

public final class ChartDimensionsModel {
	
	private double width;
	private double height;
	private double chartLeft;
	private double chartRight;
	private double chartBottom;
	private double chartTop;
	private double chartHeight;
	private double chartWidth;

	public ChartDimensionsModel(double width, double height, double margin) {
		this.width = width;
		this.height = height;
		this.chartLeft = margin;
		this.chartRight = width-margin;
		this.chartBottom = height-margin;
		this.chartTop = margin;
		this.chartHeight = chartBottom-chartTop;
		this.chartWidth = chartRight-margin;
    }
	
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	public double getChartLeft() { return chartLeft; }
	public double getChartRight() { return chartRight; }
	public double getChartBottom() { return chartBottom; }
	public double getChartTop() { return chartTop; }
	public double getChartHeight() { return chartHeight; }
	public double getChartWidth() { return chartWidth; }
	
}
