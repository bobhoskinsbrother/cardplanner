package uk.co.itstherules.yawf.view;

import uk.co.itstherules.string.io.PassthroughStringLoader;
import uk.co.itstherules.string.io.StringLoader;

public final class TextStringView extends BaseTextView {

	public TextStringView(String string) { super(string); }
	@Override protected StringLoader stringLoader() { return new PassthroughStringLoader(); }
    public String getTitle() { return this.getClass().getSimpleName(); }

}
