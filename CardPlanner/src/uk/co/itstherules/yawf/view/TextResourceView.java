package uk.co.itstherules.yawf.view;

import uk.co.itstherules.string.io.ResourcePathToStringLoader;
import uk.co.itstherules.string.io.StringLoader;

public final class TextResourceView extends BaseTextView {

	public TextResourceView(String filePath) {
		super(filePath);
	}

	@Override protected StringLoader stringLoader() {
		return new ResourcePathToStringLoader();
    }
    public String getTitle() { return this.getClass().getSimpleName(); }
}
