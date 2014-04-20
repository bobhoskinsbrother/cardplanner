package uk.co.itstherules.yawf.model;

import java.io.IOException;
import java.io.OutputStream;


public interface AttachmentModel<T> extends IdentityDeleteable<T> {

	boolean isImage();
	boolean isFile();
	boolean isNull();
	String getName();
	void streamThumbnailTo(OutputStream outputStream) throws IOException;
	void streamTo(OutputStream outputStream) throws IOException;
	void delete();

}
