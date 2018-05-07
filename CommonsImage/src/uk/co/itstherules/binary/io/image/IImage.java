package uk.co.itstherules.binary.io.image;

import java.io.IOException;
import java.io.OutputStream;

public interface IImage {
	
	public String getAbsolutePath();

	public String getName();
	
	public int getWidth();
	
	public int getHeight();
	
	public void scale(int width, int height, boolean highQuality, boolean maintainAspect);
	
	public void rotate(int rotations);
	
	public void streamTo(OutputStream outputStream) throws IOException;

	public void save() throws IOException;

	public void saveAs(String absolutePath) throws IOException;
}
