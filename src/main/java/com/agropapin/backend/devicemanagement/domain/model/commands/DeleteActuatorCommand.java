package com.agropapin.backend.devicemanagement.domain.model.commands;

import java.util.UUID;

public record DeleteActuatorCommand(
        UUID actuatorId
) {
}
