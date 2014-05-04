package uk.co.itstherules.string.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePathToStringLoader implements StringLoader {

	public String asString(String fileName) throws FileNotFoundException {
		String separator = System.getProperty("line.separator");
		String line = "";
		StringBuffer buffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(getReader(fileName));
		try {
			while ((line = bufferedReader.readLine())!=null) {
				buffer.append(line);
				buffer.append(separator);
			}
		} catch (IOException ignored) {
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException ignored) {
			}
		}
		if(buffer.length() <= separator.length()){
			return buffer.toString().trim();
		}
		return buffer.substring(0, buffer.length()-separator.length());
	}
	
	public List<String> asList(String fileName) throws FileNotFoundException {
		String line = "";
		List<String> buffer = new ArrayList<String>();
		BufferedReader bufferedReader = new BufferedReader(getReader(fileName));
		try {
			while ((line = bufferedReader.readLine())!=null) {
				buffer.add(line);
			}
		} catch (IOException ignored) {
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException ignored) {
			}
		}
		return buffer;
	}
	
	protected abstract Reader getReader(String fileName) throws FileNotFoundException;

}