package com.schemaregistry.config;

public class TransformationConfig {
    private final String configPath;
    private final String systemASchemaPath;
    private final String systemBSchemaPath;

    public TransformationConfig(String configPath, String systemASchemaPath, String systemBSchemaPath) {
        this.configPath = configPath;
        this.systemASchemaPath = systemASchemaPath;
        this.systemBSchemaPath = systemBSchemaPath;
    }

    public String getConfigPath() { return configPath; }
    public String getSystemASchemaPath() { return systemASchemaPath; }
    public String getSystemBSchemaPath() { return systemBSchemaPath; }
}
