package com.schemaregistry.transformer;

import java.util.Map;
import java.util.function.Function;

public interface FunctionRegistry {
    void registerFunction(String name, Function<Object, Object> function);
    Function<Object, Object> getFunction(String name);
    boolean hasFunction(String name);
}
