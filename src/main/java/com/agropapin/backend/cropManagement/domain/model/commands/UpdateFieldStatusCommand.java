package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.FieldStatus;

import java.util.UUID;

public record UpdateFieldStatusCommand(
        UUID fieldId,
        String farmerUserId,
        FieldStatus newStatus
) {
}
