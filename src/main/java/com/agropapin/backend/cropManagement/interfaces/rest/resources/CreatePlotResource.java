package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePlotResource(
        String plotName,
        BigDecimal plotArea
) {
}
