package com.agropapin.backend.cropManagement.interfaces.rest.resources;

public record IdealConditionsResource(
        String sunlight,
        Float minTemperatureC,
        Float maxTemperatureC,
        Float idealSoilPHMin,
        Float idealSoilPHMax,
        String wateringNeeds
) {
}
