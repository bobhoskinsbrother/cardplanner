package uk.co.itstherules.yawf.model;

import javax.persistence.Entity;

import org.apache.commons.fileupload.FileItem;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;


@Entity 
public final class FileModel extends IdentifiableDeleteableModel<FileModel> {
	
	private transient FileItem fileItem;
	private String fileName;
	
	public FileModel() {
    }
	
	public FileModel(FileItem fileItem) {
		this.fileItem = fileItem;
		this.fileName = fileItem.getName();
    }
	
	public FileItem getFileItem() { return fileItem; }
	public String getFileName() { return fileName; }

    @Override
    public FileModel defaultSetup(ObjectCache objectCache) {
        return this;
    }
}
