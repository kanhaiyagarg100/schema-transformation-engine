// TransformationRule.java
package com.schemaregistry.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransformationRule {
    @JsonProperty("source_field")
    private String sourceField;

    @JsonProperty("target_field")
    private String targetField;

    @JsonProperty("transformation_function")
    private String transformationFunction;

    @JsonProperty("default_value")
    private Object defaultValue;

    // Constructors
    public TransformationRule() {}

    public TransformationRule(String sourceField, String targetField) {
        this.sourceField = sourceField;
        this.targetField = targetField;
    }

    // Getters and setters
    public String getSourceField() { return sourceField; }
    public void setSourceField(String sourceField) { this.sourceField = sourceField; }

    public String getTargetField() { return targetField; }
    public void setTargetField(String targetField) { this.targetField = targetField; }

    public String getTransformationFunction() { return transformationFunction; }
    public void setTransformationFunction(String transformationFunction) {
        this.transformationFunction = transformationFunction;
    }

    public Object getDefaultValue() { return defaultValue; }
    public void setDefaultValue(Object defaultValue) { this.defaultValue = defaultValue; }
}
