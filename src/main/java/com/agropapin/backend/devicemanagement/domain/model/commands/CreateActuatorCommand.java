package com.agropapin.backend.devicemanagement.domain.model.commands;

import com.agropapin.backend.devicemanagement.domain.model.enums.ActuatorType;

import java.util.UUID;

public record CreateActuatorCommand(
        String serialNumber,
        UUID plotId,
        String model,
        String version,
        ActuatorType actuatorType
) {
}
