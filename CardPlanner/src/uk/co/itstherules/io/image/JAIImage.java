package uk.co.itstherules.io.image;


final class JAIImage {//extends BaseImage {
//
//	private static final RenderingHints RENDERING_HINTS = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//	private PlanarImage image;
//
//	JAIImage(String fileName) throws FileNotFoundException {
//		super(fileName);
//		this.fileName = fileName;
//		FileInputStream inputStream = new FileInputStream(this.fileName);
//		SeekableStream seekableStream = SeekableStream.wrapInputStream(inputStream, true);
//		RenderedOp image = JAI.create("stream", seekableStream);
//		((OpImage) image.getRendering()).setTileCache(null); 
//		this.image = image;
//	}
//	
//	public void crop(Point top, Point bottom) {
//		if (top == null || bottom == null) { return; }
//		float x = top.x;
//		float y = top.y;
//		float width = bottom.x - top.x;
//		float height =  bottom.y - top.y;
//		this.image = CropDescriptor.create(image, new Float(x), new Float(y), new Float(width), new Float(height), RENDERING_HINTS);
//	}
//
//	public void scale(int width, int height, boolean highQuality, boolean maintainAspect) {
//		int imageWidth = image.getWidth();
//		int imageHeight = image.getHeight();
//		if (width == -1) {
//			width = imageWidth;
//			maintainAspect = true;
//		}
//		if (height == -1) {
//			height = imageHeight;
//			maintainAspect = true;
//		}
//		float xScale, yScale;
//		xScale = (float) width / (float) imageWidth;
//		yScale = (float) height / (float) imageHeight;		
//		if (maintainAspect) {
//			float scale = Math.min(xScale, yScale);
//			xScale = scale;
//			yScale = scale;
//		}
//		ParameterBlock parameterBlock = new ParameterBlock();
//		parameterBlock.addSource(image);
//		parameterBlock.add(yScale);
//		parameterBlock.add(yScale);
//		parameterBlock.add(0.0F);
//		parameterBlock.add(0.0F);
//		if (!highQuality) {
//			parameterBlock.add(new InterpolationNearest());
//		} else {
//			parameterBlock.add(new InterpolationBicubic2(8));
//		}
//		this.image = JAI.create("scale", parameterBlock, RENDERING_HINTS);
//	}
//
//	public void rotate(int rotateAmount) {
//		if (rotateAmount == 0) {
//			return;
//		}
//		TransposeType type = TransposeDescriptor.ROTATE_90;
//		switch (rotateAmount) {
//			case 90:
//				type = TransposeDescriptor.ROTATE_90;
//				break;
//			case 180:
//				type = TransposeDescriptor.ROTATE_180;
//				break;
//			case -90:
//				type = TransposeDescriptor.ROTATE_270;
//				break;
//		}
//		this.image = TransposeDescriptor.create(image, type, RENDERING_HINTS);		
//	}
//
//	public int getWidth() {
//		return image.getWidth();
//	}
//
//	public int getHeight() {
//		return image.getHeight();
//	}
//
//	public void save() throws IOException {
//		saveAs(fileName);
//	}
//	
//	public void saveAs(String fileName) throws IOException {
//		ImageIO.write(image, new Suffix().manipulate(fileName), new File(fileName));
//	}
//
//	public void streamTo(OutputStream outputStream) throws IOException {
//		ImageIO.write(image, new Suffix().manipulate(fileName), outputStream);
//	}
}
