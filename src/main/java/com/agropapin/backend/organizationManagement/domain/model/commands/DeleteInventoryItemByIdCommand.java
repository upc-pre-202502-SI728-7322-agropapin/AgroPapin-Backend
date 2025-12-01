package com.agropapin.backend.organizationManagement.domain.model.commands;

import java.util.UUID;

public record DeleteInventoryItemByIdCommand(
        UUID inventoryItemId
) {
}
