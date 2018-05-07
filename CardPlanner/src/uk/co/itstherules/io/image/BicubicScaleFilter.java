package uk.co.itstherules.io.image;

import java.awt.*;
import java.awt.image.*;

public class BicubicScaleFilter extends AbstractBufferedImageOp {

	private final int width;

	private final int height;

	public BicubicScaleFilter(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public BufferedImage filter(BufferedImage source, BufferedImage destination) {
		if (destination == null) {
			ColorModel colourModel = source.getColorModel();
			destination = new BufferedImage(colourModel, colourModel.createCompatibleWritableRaster(width, height), colourModel.isAlphaPremultiplied(), null);
		}

		Graphics2D g = destination.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(source, 0, 0, width, height, null);
		g.dispose();

		return destination;
	}

	public String toString() {
		return "Bicubic Scale";
	}

}
