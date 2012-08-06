package uk.co.itstherules.binary.io.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import uk.co.itstherules.string.manipulation.Suffix;

final class AWTImage extends BaseImage {
	private BufferedImage image;

	AWTImage(String fileName) throws FileNotFoundException {
		super(fileName);
		try {
			this.image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new FileNotFoundException();
		}
	}

	public void scale(int width, int height, boolean highQuality,
	        boolean maintainAspect) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		if (maintainAspect) {
			double scale = new ScaleCalculator().calculate((double) imageWidth,
			        (double) imageHeight, (double) width, (double) height);
			width = (int) (imageWidth * scale);
			height = (int) (imageHeight * scale);
		}
		BicubicScaleFilter op = new BicubicScaleFilter(width, height);
		this.image = op.filter(image, null);
	}

	public void rotate(int rotateAmount) {
		if (rotateAmount == 0) {
			return;
		}
		Flip flip = null;
		switch (rotateAmount) {
			case 90:
				flip = Flip.CLOCKWISE_90_DEGREES;
				break;
			case 180:
				flip = Flip.OVER_180_DEGREES;
				break;
			case -90:
				flip = Flip.COUNTER_CLOCKWISE_90_DEGREES;
				break;
			default:
				break;
		}
		this.image = new FlipFilter(flip).filter(image, null);
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public void save() throws IOException {
		saveAs(fileName);
	}

	public void saveAs(String fileName) throws IOException {
		ImageIO.write(image, new Suffix().manipulate(fileName), new File(
		        fileName));
	}

	public void streamTo(OutputStream outputStream) throws IOException {
		ImageIO.write(image, new Suffix().manipulate(fileName), outputStream);
	}
}
