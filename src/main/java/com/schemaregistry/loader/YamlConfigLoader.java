package com.schemaregistry.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schemaregistry.exception.TransformationException;
import com.schemaregistry.model.TransformationRules;

import java.io.IOException;
import java.io.InputStream;

public class YamlConfigLoader implements ConfigLoader {
    private final ObjectMapper yamlMapper;

    // Dependency injection via constructor
    public YamlConfigLoader(ObjectMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
    }

    @Override
    public TransformationRules loadTransformationRules(String filePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new TransformationException("Configuration file not found: " + filePath);
            }
            return yamlMapper.readValue(inputStream, TransformationRules.class);
        } catch (IOException e) {
            throw new TransformationException("Failed to load transformation rules from: " + filePath, e);
        }
    }
}
