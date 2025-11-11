package com.agropapin.backend.telemetryingestion.interfaces.rest.resources;

import lombok.Builder;

import java.util.UUID;

public record AvgReadingResource(
        UUID plotId,
        String serialNumber,
        float avgTemperature,
        float avgHumidity,
        float avgSoilMoisture,
        String lastUpdated
) {
}
