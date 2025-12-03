package com.agropapin.backend.organizationManagement.domain.model.commands;

import com.agropapin.backend.organizationManagement.domain.model.valueobjects.SupplyCategory;
import java.util.UUID;

public record UpdateInventoryItemCommand(
        UUID itemId,
        String name,
        String description,
        SupplyCategory category
) {
}
