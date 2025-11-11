package com.agropapin.backend.devicemanagement.interfaces.rest.resources;

import com.agropapin.backend.devicemanagement.domain.model.enums.SensorType;

import java.util.UUID;

public record CreateSensorResource(
        String serialNumber,
        UUID plotId,
        String model,
        String version,
        SensorType sensorType
) {
}
