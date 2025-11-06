package com.agropapin.backend.cropManagement.domain.model.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePlotCommand(
        String plotName,
        BigDecimal area,
        UUID fieldId
) {
}
