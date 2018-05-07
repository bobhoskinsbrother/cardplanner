package uk.co.itstherules.yawf.model.serializer;


public interface ObjectSerializer<I,O> {
	
	O serialize(I object, String... includes);
	I deSerialize(O objects);

}