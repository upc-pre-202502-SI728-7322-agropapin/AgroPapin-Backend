package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import java.util.UUID;

public record UsageStatisticResource(
        UUID itemId,
        String itemName,
        double totalAmountUsed,
        String unit
) {
}
