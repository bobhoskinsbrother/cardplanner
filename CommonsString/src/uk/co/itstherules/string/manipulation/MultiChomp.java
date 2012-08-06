package uk.co.itstherules.string.manipulation;

import java.util.Collection;

public final class MultiChomp implements StringManipulator{

	private final Collection<String> toChomp;

	public MultiChomp(Collection<String> toChomp) {
		this.toChomp = toChomp;
    }

	public String manipulate(String text) {
		for (String modifier : toChomp) {
			text = new Chomp(modifier).manipulate(text);
        }
		return text;
    }
}
