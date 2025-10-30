package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record NutrientNeeds(
       String nitrogen,
       String phosphorus,
       String potassium
) {
    public NutrientNeeds() {
        this("", "", "");
    }
}
