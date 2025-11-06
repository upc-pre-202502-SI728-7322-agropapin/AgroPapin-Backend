package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.math.BigDecimal;

public record UpdatePlotRecourse(
        String plotName,
        BigDecimal plotArea
) {
}
