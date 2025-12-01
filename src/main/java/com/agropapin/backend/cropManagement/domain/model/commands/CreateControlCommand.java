package com.agropapin.backend.cropManagement.domain.model.commands;

import java.time.LocalDate;
import java.util.UUID;

public record CreateControlCommand(
        LocalDate date,
        String stateLeaves,
        String stateStem,
        String soilMoisture,
        UUID plantingId,
        UUID plotId
) {
}
