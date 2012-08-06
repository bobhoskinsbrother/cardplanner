package uk.co.itstherules.yawf.model;



public final class DescribeClass {
	
	private DescribeClass() { }

	public static ClassDescriptionModel[] are(Class<?>... classes) {
		ClassDescriptionModel[] array = new ClassDescriptionModel[classes.length];
		for (int j = 0; j < classes.length; j++) {
			Class<?> current = classes[j];
			array[j] = is(current);
        }
	    return array;
    }
	
	public static ClassDescriptionModel is(Class<?> described, ClassDescriptionModel... generics) {
		return new ClassDescriptionModel(described, generics);
	}

	public static ClassDescriptionModel[] generics(Class<?>... classes) {
	    return are(classes);
    }
}
