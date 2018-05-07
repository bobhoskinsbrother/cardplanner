package uk.co.itstherules.yawf.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.io.StreamForwarder;
import uk.co.itstherules.io.image.AWTImageOps;
import uk.co.itstherules.string.manipulation.Append;
import uk.co.itstherules.string.manipulation.CompositeStringManipulator;
import uk.co.itstherules.string.manipulation.Suffix;
import uk.co.itstherules.yawf.ApplicationInfo;
import uk.co.itstherules.yawf.file.FileRepository;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.UseBinder;
import uk.co.itstherules.yawf.inbound.binders.FileModelBinder;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity  
public final class SimpleAttachmentModel extends IdentifiableDeleteableModel<SimpleAttachmentModel> implements FileIdentityDeleteable<SimpleAttachmentModel>, AttachmentModel<SimpleAttachmentModel> {
	
	@OneToOne(cascade=CascadeType.PERSIST) @QueryKey("attachment") @UseBinder(FileModelBinder.class) @NotNull private FileModel fileModel;
	@QueryKey("applicationName") @NotNull private String applicationName;

    @Override
    public SimpleAttachmentModel defaultSetup(ObjectCache objectCache) {
        return this;
    }

    private enum ImageExtensions {
		JPG(".jpg"), JPEG(".jpeg"), GIF(".gif"), PNG(".png");
		private final String extension;
		private ImageExtensions(String extension) { this.extension = extension; }
		@Override public String toString() { return extension; }
	};
	
	public SimpleAttachmentModel() { }
	
	private File getNextFile() {
		Long latest = new FileRepository().nextIncrementIn(getAttachmentDirectory());
		String fullPath = MessageFormat.format("{0}/{1,number,integer}", getAttachmentDirectory().getAbsolutePath(), latest);
		return new File(fullPath);
	}
	
	private File getAttachmentDirectory() {
		String path = ApplicationInfo.getUploadPath(applicationName) + getIdentity();
		return new File(path);
	}
	
	private File getFile() {
		Long latest = new FileRepository().latestIn(getAttachmentDirectory());
		String fullPath = MessageFormat.format("{0}/{1,number,integer}", getAttachmentDirectory().getAbsolutePath(), latest);
		return new File(fullPath);
	}

	public boolean isFile() {
		return !isImage();
	}

	public boolean isImage() {
		String name = getName();
		for (ImageExtensions extension : ImageExtensions.values()) {
			if(name.toLowerCase().endsWith(extension.toString().toLowerCase())){
	        	return true;
	        }
        }
		return false;
	}
	
	public boolean isNull() { return getInvisible(); }

	public void streamThumbnailTo(OutputStream outputStream) throws IOException {
		if(isImage() && !isNull()) {
			File thumbnailFile = getThumbnailFile();
			if(!thumbnailFile.exists()){
				AWTImageOps ops = new AWTImageOps();
				BufferedImage scaled = ops.scale(getFile(), 90, 90, true, true);
				ops.save(thumbnailFile, scaled);
			}
			streamTo(thumbnailFile, outputStream);
		} else {
			streamTo(outputStream);
		}
	}

	private File getThumbnailFile() {
	    String path = getFile().getAbsolutePath();
	    File thumbnailFile = new File(getThumbnailName(path));
	    return thumbnailFile;
    }

	public void streamTo(OutputStream outputStream) throws IOException {
		streamTo(getFile(), outputStream);
    }

	private void streamTo(File file, OutputStream outputStream) throws IOException {
		new StreamForwarder().forward(new FileInputStream(file), outputStream);
    }

	public String getThumbnailName(String name) {
		String originalFileName = getName();
		CompositeStringManipulator compositeStringManipulator = new CompositeStringManipulator(new Append("_Thumbnail."), new Append(new Suffix().manipulate(originalFileName)));
		return compositeStringManipulator.manipulate(name);
    }

	public String getName() {
		if(fileModel==null) { return "Empty"; }
		return this.fileModel.getFileName();
    }

	public void write() {
		if(!getInvisible()) {
			try {
				File directory = getAttachmentDirectory();
				if(!directory.exists()) { directory.mkdirs(); }
		        File nextFile = getNextFile();
				fileModel.getFileItem().write(nextFile);
	        } catch (Exception e) {
		        throw new RuntimeException(e);
	        }
		}
    }

}