package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import static uk.co.itstherules.yawf.inbound.annotations.processor.FieldRepresentation.FieldType.*;

class QueryKeyAnnotationsProcessor {

    public <I> Map<String, FieldRepresentation> process(I instance) throws IllegalAccessException {
        Map<String, FieldRepresentation> map = new LinkedHashMap<>();
        process("", instance, map);
        return map;
    }

    private <I> void process(String prefix, I instance, Map<String, FieldRepresentation> map) throws IllegalAccessException {
        Class<?> classForInstance = instance.getClass();
        Field[] fields = classForInstance.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(QueryKey.class)) {
                QueryKey queryKey = field.getAnnotation(QueryKey.class);
                String key = queryKey.value();
                if (!"".equals(prefix)) {
                    key = prefix + "." + key;
                }
                if (map.containsKey(key)) {
                    throw new IllegalStateException("@QueryKey(\"" + key + "\") already exists on this model");
                }
                final Class<?> type = field.getType();
                FieldRepresentation.FieldType fieldType = findFieldType(type);
                Object value = field.get(instance);
                boolean shouldRetrieveFromCache = queryKey.cache() == CacheInstruction.FromCache;
                if (fieldType == OBJECT) {
                    if (value == null) {
                        throw new IllegalStateException(
                                type.getSimpleName() + " " + classForInstance.getSimpleName() +
                                "." + field.getName() + " is an object, and will be mapped, thus it requires a non-null value");
                    }
                    if(queryKey.follow()) {
                        process(key, value, map);
                    }
                }
                map.put(key, new FieldRepresentation(instance, fieldType, value, shouldRetrieveFromCache));
            }
        }
    }

    private FieldRepresentation.FieldType findFieldType(Class<?> type) {
        if (String.class.equals(type)) {
            return STRING;
        }
        if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
            return INTEGER;
        }
        return OBJECT;
    }
}
