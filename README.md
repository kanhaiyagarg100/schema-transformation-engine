# Schema Transformation Engine

A flexible Java application that converts data between different schema formats using configurable transformation rules. Built with SOLID principles and dependency injection, it demonstrates professional software architecture patterns suitable for production systems.

## Overview

The **Schema Transformation Engine** converts data bidirectionally between different schema formats (System A ↔ System B) while applying configurable transformations and validating data integrity through JSON schema validation.

## Key Architecture Components

### Core Classes

#### **SchemaTransformationEngine**
Main entry point providing `transformSystemAToB()` and `transformSystemBToA()` methods. Acts as a facade hiding internal complexity.

#### **SchemaTransformationEngineBuilder**
Implements Builder pattern for clean object construction. Allows configuration customization and custom transformation function registration.

#### **TransformationConfig**
Configuration holder managing file paths for YAML rules and JSON schemas.

### Model Classes

#### **TransformationRules**
POJO representing the complete transformation ruleset loaded from YAML, containing bidirectional transformation rules.

#### **TransformationRule**
Individual transformation rule defining source field, target field, transformation function, and default values.

### Validation Layer

#### **SchemaValidator** (Interface)
Defines contract for data validation against JSON schemas.

#### **JsonSchemaValidator**
Concrete implementation using NetworkNT JSON Schema Validator for input/output data validation.

### Transformation Layer

#### **SchemaTransformer** (Interface)
Defines transformation contract for bidirectional data conversion.

#### **DefaultSchemaTransformer**
Core transformation logic orchestrating validation, field mapping, and function application.

#### **TransformationStrategy** (Interface)
Strategy pattern interface for applying transformation functions to field values.

#### **DefaultTransformationStrategy**
Default implementation applying registered transformation functions based on rule configuration.

#### **FunctionRegistry** (Interface)
Registry pattern for managing transformation functions.

#### **DefaultFunctionRegistry**
Concrete registry implementation storing and retrieving transformation functions.

### Configuration Loading

#### **ConfigLoader** (Interface)
Abstraction for loading configuration from various sources.

#### **YamlConfigLoader**
YAML-specific implementation using Jackson for parsing transformation rules.

### Exception Handling

#### **TransformationException**
Custom runtime exception for transformation and validation errors.

## Configuration Files

**transformation-rules.yaml**: YAML file defining field mappings and transformation functions for bidirectional conversion.

**JSON Schema files**: Validation schemas ensuring data structure compliance for both systems.

## Dependency Injection Pattern

The system uses Constructor Dependency Injection without frameworks. Dependencies are injected via constructors, enabling easy testing with mock objects and loose coupling between components.

## Data Flow

Input Data → Schema Validation → Field Mapping → Function Application → Output Validation → Result

## Design Principles

### SOLID Principles Applied
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible without modification
- **Liskov Substitution**: Interface implementations are interchangeable
- **Interface Segregation**: Small, focused interfaces
- **Dependency Inversion**: Depends on abstractions

### Design Patterns Used
- **Builder Pattern**: Clean object construction
- **Strategy Pattern**: Pluggable transformation approaches
- **Facade Pattern**: Simplified interface
- **Dependency Injection**: Loose coupling and testability

## Extension Points

- **Custom transformation functions**: Register via builder
- **New config formats**: Implement `ConfigLoader` interface
- **Alternative validation**: Implement `SchemaValidator` interface
- **Custom transformation logic**: Implement `TransformationStrategy` interface

## Features

- Bi-directional transformation (A↔B)
- JSON Schema validation for data integrity
- YAML-based configuration
- Custom transformation functions support
- Comprehensive error handling
- SOLID principles implementation

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Build and Run
cd /to/extracted/project/dir
mvn clean compile
mvn resources:resources
mvn exec:java -Dexec.mainClass="com.schemaregistry.Main"

### Notes:
I have included some basic tests in the Main function itself, ideally we would write them in a separate test file.
