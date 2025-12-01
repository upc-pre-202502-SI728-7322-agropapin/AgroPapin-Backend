package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.enums.CropStatus;

import java.util.UUID;

public record UpdatePlantingStatusCommand(
        UUID plantingId,
        UUID plotId,
        CropStatus status) {
}
