package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateControlResource(
        LocalDate date,
        String stateLeaves,
        String stateStem,
        String soilMoisture
) {
}
