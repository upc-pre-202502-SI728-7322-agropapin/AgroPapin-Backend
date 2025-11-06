package com.agropapin.backend.cropManagement.domain.model.commands;

import java.math.BigDecimal;

public record CreateFieldCommand(
        String creatorUserId,
        String fieldName,
        String location,
        BigDecimal area
) {
}
