package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.enums.FieldStatus;

import java.util.UUID;

public record UpdateFieldStatusCommand(
        UUID fieldId,
        String farmerUserId,
        FieldStatus newStatus
) {
}
