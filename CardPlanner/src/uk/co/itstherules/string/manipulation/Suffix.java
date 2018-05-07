package uk.co.itstherules.string.manipulation;

public final class Suffix implements StringManipulator{

	public String manipulate(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
