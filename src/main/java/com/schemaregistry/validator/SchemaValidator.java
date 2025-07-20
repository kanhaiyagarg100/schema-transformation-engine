package com.schemaregistry.validator;

import java.util.Map;

public interface SchemaValidator {
    boolean validate(Map<String, Object> data, String schemaPath);
    String getValidationErrors();
}
