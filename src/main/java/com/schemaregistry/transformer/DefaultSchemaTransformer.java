package com.schemaregistry.transformer;

import com.schemaregistry.config.TransformationConfig;
import com.schemaregistry.exception.TransformationException;
import com.schemaregistry.model.TransformationRule;
import com.schemaregistry.model.TransformationRules;
import com.schemaregistry.validator.SchemaValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultSchemaTransformer implements SchemaTransformer {
    private final TransformationRules rules;
    private final SchemaValidator validator;
    private final TransformationStrategy strategy;
    private final TransformationConfig config;

    // All dependencies injected via constructor
    public DefaultSchemaTransformer(TransformationRules rules,
                                  SchemaValidator validator,
                                  TransformationStrategy strategy,
                                  TransformationConfig config) {
        this.rules = rules;
        this.validator = validator;
        this.strategy = strategy;
        this.config = config;
    }

    @Override
    public Map<String, Object> transformAtoB(Map<String, Object> sourceData) {
        return transform(sourceData, rules.getAToB(), config.getSystemASchemaPath());
    }

    @Override
    public Map<String, Object> transformBtoA(Map<String, Object> sourceData) {
        return transform(sourceData, rules.getBToA(), config.getSystemBSchemaPath());
    }

    private Map<String, Object> transform(Map<String, Object> sourceData,
                                        List<TransformationRule> transformationRules,
                                        String sourceSchemaPath) {
        // Validate input data
        if (!validator.validate(sourceData, sourceSchemaPath)) {
            throw new TransformationException("Source data validation failed: " +
                validator.getValidationErrors());
        }

        Map<String, Object> result = new HashMap<>();

        for (TransformationRule rule : transformationRules) {
            Object sourceValue = sourceData.get(rule.getSourceField());
            Object transformedValue = strategy.transform(sourceValue, rule);

            if (transformedValue != null || rule.getDefaultValue() != null) {
                result.put(rule.getTargetField(), transformedValue);
            }
        }

        return result;
    }
}
