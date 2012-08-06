package uk.co.itstherules.string.manipulation;


public final class TitleifyFile implements StringManipulator {

	private StringManipulator delegate;

	public TitleifyFile(String extension) {
		this.delegate = new CompositeStringManipulator(new AppendageRemover(extension), new SplitCamelCase());
	}
	
	public String manipulate(String text) {
		return delegate.manipulate(text);
	}

}
