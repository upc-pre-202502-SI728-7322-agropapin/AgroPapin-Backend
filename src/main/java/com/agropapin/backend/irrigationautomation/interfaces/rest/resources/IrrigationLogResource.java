package com.agropapin.backend.irrigationautomation.interfaces.rest.resources;

import java.time.Instant;
import java.util.UUID;

/**
 * Resource (DTO) representing a single irrigation log entry.
 */
public record IrrigationLogResource(
        UUID logId,
        UUID plotId,
        Instant decisionTimestamp,
        String decision,
        String reason,
        double humidityReading,
        double humidityThreshold
) {
}
