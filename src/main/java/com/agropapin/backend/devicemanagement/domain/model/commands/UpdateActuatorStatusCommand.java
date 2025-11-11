package com.agropapin.backend.devicemanagement.domain.model.commands;

import com.agropapin.backend.devicemanagement.domain.model.enums.DeviceStatus;

import java.util.UUID;

public record UpdateActuatorStatusCommand(
        UUID plotId,
        UUID actuatorId,
        DeviceStatus status
) {
}
