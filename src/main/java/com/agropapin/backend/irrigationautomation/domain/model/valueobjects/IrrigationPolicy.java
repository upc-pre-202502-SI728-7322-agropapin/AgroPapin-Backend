package com.agropapin.backend.irrigationautomation.domain.model.valueobjects;

import java.util.UUID;

/**
 * A Value Object representing the irrigation policy for a plot.
 * This is a DTO provided by a Facade from another Bounded Context.
 */
public record IrrigationPolicy(
    UUID plotId,
    double humidityThreshold,
    int defaultDurationMinutes,
    UUID actuatorId
) {
    public boolean isPolicyDefined() {
        return actuatorId != null && humidityThreshold > 0;
    }
}
