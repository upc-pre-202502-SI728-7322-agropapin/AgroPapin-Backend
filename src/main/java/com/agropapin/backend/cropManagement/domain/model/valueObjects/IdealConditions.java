package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IdealConditions {

    @Column(name = "sunlight", length = 50)
    private String sunlight;         // Ej: "FULL_SUN"

    @Column(name = "min_temperature_c")
    private Float minTemperatureC;

    @Column(name = "max_temperature_c")
    private Float maxTemperatureC;

    @Column(name = "ideal_soil_ph_min")
    private Float idealSoilPHMin;

    @Column(name = "ideal_soil_ph_max")
    private Float idealSoilPHMax;

    @Column(name = "watering_needs", length = 50)
    private String wateringNeeds;    // Ej: "MEDIUM"
}
