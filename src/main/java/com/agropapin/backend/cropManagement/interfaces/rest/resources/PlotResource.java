package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.PlotStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PlotResource(
        UUID plotId,
        String plotName,
        BigDecimal area,
        PlotStatus status
) {
}
