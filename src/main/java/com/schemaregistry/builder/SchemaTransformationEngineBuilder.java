package com.schemaregistry.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.schemaregistry.SchemaTransformationEngine;
import com.schemaregistry.config.TransformationConfig;
import com.schemaregistry.loader.ConfigLoader;
import com.schemaregistry.loader.YamlConfigLoader;
import com.schemaregistry.model.TransformationRules;
import com.schemaregistry.transformer.*;
import com.schemaregistry.validator.JsonSchemaValidator;
import com.schemaregistry.validator.SchemaValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SchemaTransformationEngineBuilder {
    private TransformationConfig config;
    private final Map<String, Function<Object, Object>> customFunctions = new HashMap<>();
    private ConfigLoader configLoader;
    private SchemaValidator validator;
    private TransformationStrategy strategy;

    public SchemaTransformationEngineBuilder withConfig(TransformationConfig config) {
        this.config = config;
        return this;
    }

    public SchemaTransformationEngineBuilder withCustomFunction(String name, Function<Object, Object> function) {
        this.customFunctions.put(name, function);
        return this;
    }

    public SchemaTransformationEngineBuilder withConfigLoader(ConfigLoader configLoader) {
        this.configLoader = configLoader;
        return this;
    }

    public SchemaTransformationEngineBuilder withValidator(SchemaValidator validator) {
        this.validator = validator;
        return this;
    }

    public SchemaTransformationEngineBuilder withTransformationStrategy(TransformationStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public SchemaTransformationEngine build() {
        validateConfiguration();

        // Create default dependencies if not provided
        if (configLoader == null) {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            configLoader = new YamlConfigLoader(yamlMapper);
        }

        if (validator == null) {
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            validator = new JsonSchemaValidator(jsonMapper, schemaFactory);
        }

        if (strategy == null) {
            Map<String, Function<Object, Object>> allFunctions = createDefaultFunctions();
            allFunctions.putAll(customFunctions);

            FunctionRegistry functionRegistry = new DefaultFunctionRegistry(allFunctions);
            strategy = new DefaultTransformationStrategy(functionRegistry);
        }

        // Load transformation rules
        TransformationRules rules = configLoader.loadTransformationRules(config.getConfigPath());

        // Create transformer with all dependencies
        SchemaTransformer transformer = new DefaultSchemaTransformer(rules, validator, strategy, config);

        // Create and return engine
        return new SchemaTransformationEngine(transformer);
    }

    private void validateConfiguration() {
        if (config == null) {
            throw new IllegalStateException("Configuration is required. Use withConfig() to set it.");
        }
    }

    private Map<String, Function<Object, Object>> createDefaultFunctions() {
        Map<String, Function<Object, Object>> functions = new HashMap<>();

        functions.put("uppercase", value ->
            value instanceof String ? ((String) value).toUpperCase() : value);
        functions.put("lowercase", value ->
            value instanceof String ? ((String) value).toLowerCase() : value);
        functions.put("trim", value ->
            value instanceof String ? ((String) value).trim() : value);
        functions.put("reverse", value ->
            value instanceof String ? new StringBuilder((String) value).reverse().toString() : value);

        return functions;
    }
}
