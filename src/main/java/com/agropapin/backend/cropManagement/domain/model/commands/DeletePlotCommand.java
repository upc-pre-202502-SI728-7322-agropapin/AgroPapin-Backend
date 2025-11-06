package com.agropapin.backend.cropManagement.domain.model.commands;

import java.util.UUID;

public record DeletePlotCommand(
        UUID plotId,
        UUID fieldId
) {
}
