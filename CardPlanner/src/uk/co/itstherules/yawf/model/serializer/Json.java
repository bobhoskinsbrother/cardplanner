package uk.co.itstherules.yawf.model.serializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


public final class Json<T> implements ObjectSerializer<T, String> {

	public String serialize(T object) {
		return serialize(object, ""); 
    }

	public String serialize(T object, String... includes) {
		return serialize(object, Arrays.asList(includes), new ArrayList<String>()); 
    }
	
	public String serialize(T object, List<String> includes) {
		return serialize(object, includes, new ArrayList<String>());
    }

	public String serialize(T object, List<String> includes, List<String> excludes) {
		JSONSerializer serializer = new JSONSerializer();
        serializer = addIncludes(includes, serializer);
        serializer = addExcludes(excludes, serializer);
        return serializer.serialize(object);
    }

    public String deepSerialize(Object object) {
        return deepSerialize(object, new ArrayList<String>());
    }

    public String deepSerialize(Object object, List<String> includes) {
        return deepSerialize(object, includes, new ArrayList<String>());
    }
    
    public String deepSerialize(Object object, List<String> includes, List<String> excludes) {
        JSONSerializer serializer = new JSONSerializer();
        serializer = addIncludes(includes, serializer);
        serializer = addExcludes(excludes, serializer);
        return serializer.deepSerialize(object);
    }

	public T deSerialize(String objects) {
		return deSerialize(objects, new HashMap<String, Class<?>>());
    }
	
	public T deSerialize(String objects, Map<String, Class<?>> classesToUse) {
		JSONDeserializer<T> deSerializer = new JSONDeserializer<T>();
//		for (String path : classesToUse.keySet()) {
//	        deSerializer.use(path, classesToUse.get(path));
//        }
		return deSerializer.deserialize(objects);
    }


    private JSONSerializer addExcludes(List<String> excludes, JSONSerializer serializer) {
        if(excludes != null && excludes.size() > 0) {
			for (String exclude : excludes) {
				serializer = serializer.exclude(exclude);
            }
		}
        return serializer;
    }

    private JSONSerializer addIncludes(List<String> includes, JSONSerializer serializer) {
        if(includes != null && includes.size() > 0) {
            for (String include : includes) {
                serializer = serializer.include(include);
            }
        }
        return serializer;
    }
}