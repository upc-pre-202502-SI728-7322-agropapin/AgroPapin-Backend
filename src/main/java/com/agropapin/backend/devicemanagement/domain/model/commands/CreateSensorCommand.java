package com.agropapin.backend.devicemanagement.domain.model.commands;

import java.util.UUID;

public record CreateSensorCommand(
        String serialNumber,
        UUID plotId,
        String model,
        String version
) {
}
