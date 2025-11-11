package com.agropapin.backend.devicemanagement.interfaces.rest.resources;

import java.util.UUID;

public record SensorResource(
        UUID sensorId,
        String serialNumber,
        UUID plotId,
        String status,
        String model,
        String version
) {
}
