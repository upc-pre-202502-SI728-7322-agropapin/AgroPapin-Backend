package com.agropapin.backend.devicemanagement.interfaces.rest.resources;

import com.agropapin.backend.devicemanagement.domain.model.enums.ActuatorType;

import java.util.UUID;

public record CreateActuatorResource(
        String serialNumber,
        UUID plotId,
        String model,
        String version,
        ActuatorType actuatorType
) {
}
