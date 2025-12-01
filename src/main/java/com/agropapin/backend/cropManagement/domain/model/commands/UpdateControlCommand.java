package com.agropapin.backend.cropManagement.domain.model.commands;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateControlCommand(
        UUID controlId,
        LocalDate date,
        String stateLeaves,
        String stateStem,
        String soilMoisture
) {
}
