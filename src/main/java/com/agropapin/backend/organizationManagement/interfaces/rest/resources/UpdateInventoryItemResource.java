package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import com.agropapin.backend.organizationManagement.domain.model.valueobjects.SupplyCategory;

public record UpdateInventoryItemResource(
        String name,
        String description,
        SupplyCategory category
) {
}
