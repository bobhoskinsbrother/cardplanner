package uk.co.itstherules.yawf.view;

import java.io.InputStream;

public final class BinaryResourceView extends BaseBinaryView {

	public BinaryResourceView(String resourcePath) {
		super(resourcePath);
	}
	protected InputStream inputStream(){
		return BinaryResourceView.class.getClassLoader().getResourceAsStream(this.resourcePath);
	}
}
