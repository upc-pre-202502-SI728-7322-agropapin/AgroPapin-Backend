package com.agropapin.backend.telemetryingestion.interfaces.rest.resources;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for an incoming telemetry reading.
 * Using Instant for the timestamp is a best practice for time-series data.
 */
public record PlotReadingResource(
        UUID plotId,
        String serialNumber,
        Instant timestamp,
        double temperature,
        double humidity,
        double soilMoisture
) {
}
