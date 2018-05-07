package uk.co.itstherules.io.image;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class BaseImage implements IImage {

	protected String fileName;
	private String name;
	private String absolutePath;

	public BaseImage(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		File file = new File(fileName);
		this.name = file.getName();
		this.absolutePath = file.getAbsolutePath();
	}

	public String getAbsolutePath() {
		return absolutePath;
    }

	public String getName() {
		return name;
    }
}
