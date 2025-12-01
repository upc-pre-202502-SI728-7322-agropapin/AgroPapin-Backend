package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryItem;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.InventoryItemResource;

public class InventoryItemResourceFromEntityAssembler {

    public static InventoryItemResource toResourceFromEntity(InventoryItem entity) {
        return new InventoryItemResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getQuantity().getAmount(),
                entity.getQuantity().getUnit()
        );
    }
}
