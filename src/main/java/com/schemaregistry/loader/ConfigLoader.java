// ConfigLoader.java (Interface for extensibility)
package com.schemaregistry.loader;

import com.schemaregistry.model.TransformationRules;

public interface ConfigLoader {
    TransformationRules loadTransformationRules(String filePath);
}
