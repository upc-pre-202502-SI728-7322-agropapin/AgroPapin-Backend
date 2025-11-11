package com.agropapin.backend.devicemanagement.domain.model.commands;

import com.agropapin.backend.devicemanagement.domain.model.enums.DeviceStatus;

import java.util.UUID;

public record UpdateSensorStatusCommand(
        UUID plotId,
        UUID actuatorId,
        DeviceStatus status
) {
}
