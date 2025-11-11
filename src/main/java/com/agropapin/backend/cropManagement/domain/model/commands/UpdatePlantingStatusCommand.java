package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.CropStatus;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.PlotStatus;

import java.util.UUID;

public record UpdatePlantingStatusCommand(
        UUID plantingId,
        UUID plotId,
        CropStatus status) {
}
