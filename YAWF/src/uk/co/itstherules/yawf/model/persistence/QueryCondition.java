package uk.co.itstherules.yawf.model.persistence;

import java.util.Map;

public interface QueryCondition {
    @Override
    String toString();
    Map<String, Object> getParameterValues();
}
