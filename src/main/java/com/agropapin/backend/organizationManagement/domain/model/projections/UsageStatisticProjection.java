package com.agropapin.backend.organizationManagement.domain.model.projections;

import java.util.UUID;

public record UsageStatisticProjection(
        UUID itemId,
        String itemName,
        Double totalAmountUsed,
        String unit
) {
}
