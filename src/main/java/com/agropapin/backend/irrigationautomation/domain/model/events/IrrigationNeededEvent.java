package com.agropapin.backend.irrigationautomation.domain.model.events;

import java.util.UUID;

/**
 * Published by the Decision Service when irrigation is deemed necessary.
 * This event triggers the Action Service.
 */
public record IrrigationNeededEvent(
        UUID plotId,
        double currentHumidity,
        double humidityThreshold,
        int durationMinutes
) {
}
