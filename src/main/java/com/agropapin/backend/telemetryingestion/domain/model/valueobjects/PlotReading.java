package com.agropapin.backend.telemetryingestion.domain.model.valueobjects;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a single, immutable sensor reading for a plot at a specific point in time.
 * This is a Value Object because it has no identity; its value is its identity.
 */
public record PlotReading(
        UUID plotId,
        String serialNumber,
        Instant timestamp,
        Double temperature,
        Double humidity,
        Double soilMoisture
) {
    public PlotReading {
        if (plotId == null) {
            throw new IllegalArgumentException("Plot ID cannot be null.");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null.");
        }
    }
}
