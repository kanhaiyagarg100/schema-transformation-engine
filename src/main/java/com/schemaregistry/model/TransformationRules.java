// TransformationRules.java
package com.schemaregistry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TransformationRules {
    @JsonProperty("version")
    private String version;

    @JsonProperty("description")  // Add this field
    private String description;

    @JsonProperty("a_to_b")
    private List<TransformationRule> aToB;

    @JsonProperty("b_to_a")
    private List<TransformationRule> bToA;

    // Constructors
    public TransformationRules() {}

    // Add getter and setter for description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Getters and setters
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public List<TransformationRule> getAToB() { return aToB; }
    public void setAToB(List<TransformationRule> aToB) { this.aToB = aToB; }

    public List<TransformationRule> getBToA() { return bToA; }
    public void setBToA(List<TransformationRule> bToA) { this.bToA = bToA; }
}
