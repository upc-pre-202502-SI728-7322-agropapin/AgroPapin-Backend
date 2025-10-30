package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record IdealConditions(
        String sunlight,         // Ej: "FULL_SUN"
        float minTemperatureC,
        float maxTemperatureC,
        float idealSoilPH_min,
        float idealSoilPH_max,
        String wateringNeeds     // Ej: "MEDIUM"
) {
    public IdealConditions() {
        this("", 0f, 0f, 0f, 0f, "");
    }
}
