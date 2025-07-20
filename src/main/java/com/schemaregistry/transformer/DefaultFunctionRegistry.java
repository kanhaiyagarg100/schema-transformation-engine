package com.schemaregistry.transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DefaultFunctionRegistry implements FunctionRegistry {
    private final Map<String, Function<Object, Object>> functions;

    public DefaultFunctionRegistry(Map<String, Function<Object, Object>> initialFunctions) {
        this.functions = new HashMap<>(initialFunctions);
    }

    @Override
    public void registerFunction(String name, Function<Object, Object> function) {
        functions.put(name, function);
    }

    @Override
    public Function<Object, Object> getFunction(String name) {
        return functions.get(name);
    }

    @Override
    public boolean hasFunction(String name) {
        return functions.containsKey(name);
    }
}
