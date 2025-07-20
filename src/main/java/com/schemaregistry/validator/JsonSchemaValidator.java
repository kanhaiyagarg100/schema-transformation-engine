package com.schemaregistry.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.schemaregistry.exception.TransformationException;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonSchemaValidator implements SchemaValidator {
    private final ObjectMapper objectMapper;
    private final JsonSchemaFactory schemaFactory;
    private String lastValidationErrors;

    // Constructor injection
    public JsonSchemaValidator(ObjectMapper objectMapper, JsonSchemaFactory schemaFactory) {
        this.objectMapper = objectMapper;
        this.schemaFactory = schemaFactory;
    }

    @Override
    public boolean validate(Map<String, Object> data, String schemaPath) {
        try {
            InputStream schemaStream = getClass().getClassLoader().getResourceAsStream(schemaPath);
            if (schemaStream == null) {
                throw new TransformationException("Schema file not found: " + schemaPath);
            }

            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            JsonNode dataNode = objectMapper.valueToTree(data);

            Set<ValidationMessage> errors = schema.validate(dataNode);

            if (!errors.isEmpty()) {
                lastValidationErrors = errors.stream()
                    .map(ValidationMessage::getMessage)
                    .collect(Collectors.joining(", "));
                return false;
            }

            lastValidationErrors = null;
            return true;
        } catch (Exception e) {
            lastValidationErrors = "Validation failed: " + e.getMessage();
            return false;
        }
    }

    @Override
    public String getValidationErrors() {
        return lastValidationErrors;
    }
}
