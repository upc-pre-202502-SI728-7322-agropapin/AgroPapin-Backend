package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import com.agropapin.backend.organizationManagement.domain.model.valueobjects.SupplyCategory;
import java.util.UUID;

public record InventoryItemResource(
        UUID id,
        String name,
        String description,
        SupplyCategory category,
        double amount,
        String unit
) {
}
