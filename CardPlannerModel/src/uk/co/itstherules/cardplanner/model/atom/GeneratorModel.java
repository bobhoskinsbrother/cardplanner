package uk.co.itstherules.cardplanner.model.atom;

import uk.co.itstherules.yawf.model.atom.Generator;

public final class GeneratorModel implements Generator {
	
	private final String value;
	private final String root;

	public GeneratorModel(String value, String root) {
		this.value = value;
		this.root = root;
    }

	public String getUri() {
		return root;
	}

	public String getValue() {
		return value;
	}
}
