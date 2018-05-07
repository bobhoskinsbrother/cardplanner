package uk.co.itstherules.string.manipulation;

public final class Prefix implements StringManipulator {

	public String manipulate(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(".") + 1);
    }
}
