package com.agropapin.backend.devicemanagement.domain.model.commands;

import java.util.UUID;

public record DeleteSensorCommand(
        UUID sensorId
) {
}
