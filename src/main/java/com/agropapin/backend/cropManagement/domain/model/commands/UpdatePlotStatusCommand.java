package com.agropapin.backend.cropManagement.domain.model.commands;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.PlotStatus;

import java.util.UUID;

public record UpdatePlotStatusCommand(
        UUID plotId,
        UUID fieldId,
        PlotStatus plotStatus
) {
}
