package com.schemaregistry.transformer;

import java.util.Map;

public interface SchemaTransformer {
    Map<String, Object> transformAtoB(Map<String, Object> sourceData);
    Map<String, Object> transformBtoA(Map<String, Object> sourceData);
}
