package uk.co.itstherules.binary.io.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import uk.co.itstherules.string.manipulation.Suffix;

public final class AWTImageOps {

	private BufferedImage load(File file) {
		try {
	        return ImageIO.read(file);
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
	}
	
	public BufferedImage scale(File file, int targetWidth, int targetHeight, boolean highQuality, boolean maintainAspect) {
		BufferedImage image = load(file);
		int currentWidth = image.getWidth();
		int currentHeight = image.getHeight();
		if(currentHeight < targetHeight && currentWidth < targetWidth) { return image; }
		if(maintainAspect) {
			double scale = new ScaleCalculator().calculate((double)currentWidth, (double)currentHeight, (double)targetWidth, (double)targetHeight);
			targetWidth = (int) (currentWidth * scale);
			targetHeight = (int) (currentHeight * scale);
		}
		
		BicubicScaleFilter op = new BicubicScaleFilter(targetWidth, targetHeight);
		return op.filter(image, null);
	}

	public BufferedImage rotate(File file, int rotateAmount) {
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
		return new FlipFilter(flip).filter(load(file), null);		
	}
	
	public void save(File file, BufferedImage image) throws IOException {
		ImageIO.write(image, new Suffix().manipulate(file.getName()), file);
	}

	public void streamTo(File file, OutputStream outputStream) throws IOException {
		ImageIO.write(load(file), new Suffix().manipulate(file.getName()), outputStream);
	}
}
