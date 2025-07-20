package com.schemaregistry.transformer;

import com.schemaregistry.model.TransformationRule;

public interface TransformationStrategy {
    Object transform(Object value, TransformationRule rule);
}
