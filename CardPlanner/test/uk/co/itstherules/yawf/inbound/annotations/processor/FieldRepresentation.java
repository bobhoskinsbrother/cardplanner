package uk.co.itstherules.yawf.inbound.annotations.processor;

public final class FieldRepresentation {

    private final Object instance;
    private final FieldType fieldType;
    private final Object value;
    private boolean shouldRetrieveFromCache;

    public enum FieldType {
        BOOLEAN, CLASS, DATE, DOUBLE, ENUM, INTEGER, LONG, STRING, LIST, SET, MAP, OBJECT;
    }

    public FieldRepresentation(Object instance, FieldType fieldType, Object value, boolean shouldRetrieveFromCache) {
        this.instance = instance;
        this.fieldType = fieldType;
        this.value = value;
        this.shouldRetrieveFromCache = shouldRetrieveFromCache;
    }

    public boolean shouldRetrieveFromCache() {
        return shouldRetrieveFromCache;
    }

    public Object model() {
        return instance;
    }

    public FieldType fieldType() {
        return fieldType;
    }

    public Object value() {
        return value;
    }
}
