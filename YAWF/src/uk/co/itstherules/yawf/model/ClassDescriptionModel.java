package uk.co.itstherules.yawf.model;

public final class ClassDescriptionModel {
	
	private final Class<?> described;
	private final ClassDescriptionModel[] genericTypes;
	
	public ClassDescriptionModel(Class<?> described, ClassDescriptionModel... genericTypes) {
		this.described = described;
		this.genericTypes = genericTypes;
    }
	
	public Class<?> getDescribed() { return described; }
	public ClassDescriptionModel[] getGenericTypes() { return genericTypes; }
	
	@Override public String toString() {
	    StringBuffer buffer = new StringBuffer();
	    Class<?> described = getDescribed();
	    if(described.isInterface()) { buffer.append("interface "); }
	    else if(described.isEnum()) { buffer.append("enum "); }
	    else { buffer.append("class "); }
	    buffer.append(described.getName());
	    ClassDescriptionModel[] genericTypes = getGenericTypes();
	    if(genericTypes != null && genericTypes.length > 0) {
	    	buffer.append("<");
		    for (int i = 0; i < genericTypes.length; i++) {
		       	ClassDescriptionModel generic = genericTypes[i];
		       	buffer.append(generic.getDescribed().getName());
		       	if(i < genericTypes.length) {
		       		buffer.append(",");
		       	}
		    }
		    buffer.append(">");
	    }
	    String reply = buffer.toString();
	    return reply;
	}
	
}
