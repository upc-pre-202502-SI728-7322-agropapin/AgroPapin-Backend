package com.agropapin.backend.devicemanagement.interfaces.rest.resources;

import java.util.UUID;

public record ActuatorResource(
        UUID actuatorId,
        String serialNumber,
        UUID plotId,
        String status,
        String model,
        String version,
        String actuatorType
) {
}
