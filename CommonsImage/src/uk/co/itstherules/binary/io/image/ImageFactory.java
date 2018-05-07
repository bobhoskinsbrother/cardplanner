package uk.co.itstherules.binary.io.image;

import java.io.FileNotFoundException;

public class ImageFactory {
	
	public enum ImageType{ AWT, JAI };
	
	public static IImage create(String fileName, ImageType type) throws FileNotFoundException {
		if(type==ImageType.AWT) {
			return new AWTImage(fileName);
		}
//		if(type==ImageType.JAI) {
//			return new JAIImage(fileName);
//		}
		throw new IllegalArgumentException("No Image Type for "+type.name());
	}
	
}
