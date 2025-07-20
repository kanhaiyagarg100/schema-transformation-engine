package com.schemaregistry;

import com.schemaregistry.builder.SchemaTransformationEngineBuilder;
import com.schemaregistry.config.TransformationConfig;
import com.schemaregistry.exception.TransformationException;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Schema Transformation Engine with Validation Demo");
        System.out.println("================================================");

        TransformationConfig config = new TransformationConfig(
            "transformation-rules.yaml",
            "schemas/system-a-schema.json",
            "schemas/system-b-schema.json"
        );

        // Using builder pattern for clean instantiation
        SchemaTransformationEngine engine = new SchemaTransformationEngineBuilder()
            .withConfig(config)
            .withCustomFunction("capitalize", Main::capitalize)
            .build();

        // Test data - valid case
        System.out.println("\n=== Testing with VALID data ===");
        Map<String, Object> validSystemAData = Map.of(
            "first_name", "alice",
            "last_name", "smith",
            "email", "alice@example.com",
            "created_at", "2025-07-20T10:00:00Z"
        );

        performTransformationWithValidation(engine, "Valid System A Data", validSystemAData);

        // Test data - invalid case (missing required field)
        System.out.println("\n=== Testing with INVALID data (missing email) ===");
        Map<String, Object> invalidSystemAData = Map.of(
            "first_name", "bob",
            "last_name", "johnson"
            // Missing required "email" field
        );

        performTransformationWithValidation(engine, "Invalid System A Data", invalidSystemAData);

        // Test data - invalid case (wrong email format)
        System.out.println("\n=== Testing with INVALID data (bad email format) ===");
        Map<String, Object> badEmailSystemAData = Map.of(
            "first_name", "charlie",
            "last_name", "brown",
            "email", "not-a-valid-email",  // Invalid email format
            "created_at", "2025-07-20T10:00:00Z"
        );

        performTransformationWithValidation(engine, "Bad Email System A Data", badEmailSystemAData);

        // Test data - invalid case (empty string)
        System.out.println("\n=== Testing with INVALID data (empty first_name) ===");
        Map<String, Object> emptyNameSystemAData = Map.of(
            "first_name", "",  // Empty string violates minLength: 1
            "last_name", "davis",
            "email", "empty@example.com",
            "created_at", "2025-07-20T10:00:00Z"
        );

        performTransformationWithValidation(engine, "Empty Name System A Data", emptyNameSystemAData);
    }

    private static void performTransformationWithValidation(SchemaTransformationEngine engine,
                                                           String testCaseName,
                                                           Map<String, Object> inputData) {
        System.out.println("\n--- " + testCaseName + " ---");
        System.out.println("Input: " + inputData);

        try {
            // Attempt transformation (validation happens automatically inside)
            Map<String, Object> systemBResult = engine.transformSystemAToB(inputData);
            System.out.println("✅ System A → B Transformation SUCCESS");
            System.out.println("Output: " + systemBResult);

            // Try reverse transformation
            Map<String, Object> backToSystemA = engine.transformSystemBToA(systemBResult);
            System.out.println("✅ System B → A Transformation SUCCESS");
            System.out.println("Back to A: " + backToSystemA);

        } catch (TransformationException e) {
            System.out.println("❌ VALIDATION/TRANSFORMATION FAILED");
            System.out.println("Error: " + e.getMessage());

            // Extract and display validation details if available
            if (e.getMessage().contains("validation failed")) {
                System.out.println("Validation Details: This indicates the input data doesn't conform to the expected JSON schema");
            }
        } catch (Exception e) {
            System.out.println("❌ UNEXPECTED ERROR");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Object capitalize(Object value) {
        if (value instanceof String str) {
            if (str.isEmpty()) return str;
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
        return value;
    }
}
