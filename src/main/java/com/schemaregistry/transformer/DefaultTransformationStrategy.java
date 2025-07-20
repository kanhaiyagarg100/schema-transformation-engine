package com.schemaregistry.transformer;

import com.schemaregistry.model.TransformationRule;

public class DefaultTransformationStrategy implements TransformationStrategy {
    private final FunctionRegistry functionRegistry;

    // Constructor injection
    public DefaultTransformationStrategy(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }

    @Override
    public Object transform(Object value, TransformationRule rule) {
        if (value == null) {
            return rule.getDefaultValue();
        }

        String functionName = rule.getTransformationFunction();
        if (functionName != null && functionRegistry.hasFunction(functionName)) {
            return functionRegistry.getFunction(functionName).apply(value);
        }

        return value;
    }
}
