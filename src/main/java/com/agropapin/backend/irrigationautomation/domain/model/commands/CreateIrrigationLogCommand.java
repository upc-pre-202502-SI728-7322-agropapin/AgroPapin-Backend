package com.agropapin.backend.irrigationautomation.domain.model.commands;

import java.util.UUID;

public record CreateIrrigationLogCommand(
        UUID plotId,
        String decision,
        String reason,
        double humidityReading,
        double humidityThreshold
) {
}
