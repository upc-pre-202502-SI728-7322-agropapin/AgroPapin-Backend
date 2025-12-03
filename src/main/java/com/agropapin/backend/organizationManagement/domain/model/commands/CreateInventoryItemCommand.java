package com.agropapin.backend.organizationManagement.domain.model.commands;

import com.agropapin.backend.organizationManagement.domain.model.valueobjects.SupplyCategory;
import java.util.UUID;

public record CreateInventoryItemCommand(
        UUID cooperativeId,
        String name,
        String description,
        SupplyCategory category,
        double initialAmount,
        String unit
) {
}
