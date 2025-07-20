package com.schemaregistry;

import com.schemaregistry.transformer.SchemaTransformer;
import java.util.Map;

public class SchemaTransformationEngine {
    private final SchemaTransformer transformer;

    // Only accepts the transformer as dependency - no creation logic
    public SchemaTransformationEngine(SchemaTransformer transformer) {
        this.transformer = transformer;
    }

    public Map<String, Object> transformSystemAToB(Map<String, Object> systemAData) {
        return transformer.transformAtoB(systemAData);
    }

    public Map<String, Object> transformSystemBToA(Map<String, Object> systemBData) {
        return transformer.transformBtoA(systemBData);
    }
}
