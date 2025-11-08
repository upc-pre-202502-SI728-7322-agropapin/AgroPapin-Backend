package com.agropapin.backend.devicemanagement.domain.model.commands;

import com.agropapin.backend.devicemanagement.domain.model.enums.SensorType;
import com.agropapin.backend.devicemanagement.domain.model.valueobjects.DeviceModel;

import java.util.UUID;

public record CreateSensorCommand(
        String serialNumber,
        UUID plotId,
        String model,
        String version,
        SensorType sensorType
) {
}
