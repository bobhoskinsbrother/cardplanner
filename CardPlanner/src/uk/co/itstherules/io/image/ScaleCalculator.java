package uk.co.itstherules.io.image;

public class ScaleCalculator {

	public double calculate(double originalWidth, double originalHeight, double targetWidth, double targetHeight) {
		double heightScale = targetHeight / originalHeight;
		double widthScale = targetWidth / originalWidth;
		if(heightScale < 1.0 || widthScale < 1.0){
			return Math.min(heightScale, widthScale);
		}
		return Math.max(heightScale, widthScale);
	}
}
