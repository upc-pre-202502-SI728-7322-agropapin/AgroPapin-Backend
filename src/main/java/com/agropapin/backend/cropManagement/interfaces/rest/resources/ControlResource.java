package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.UUID;

public record ControlResource(
        UUID controlId,
        LocalDate date,
        String stateLeaves,
        String stateStem,
        String soilMoisture,
        UUID plantingId,
        UUID plotId
) {
}
