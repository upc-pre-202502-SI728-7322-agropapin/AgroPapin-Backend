package com.agropapin.backend.cropManagement.domain.model.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateFieldDataCommand(
        String userId,
        String fieldName,
        String location,
        BigDecimal area
) {
}
