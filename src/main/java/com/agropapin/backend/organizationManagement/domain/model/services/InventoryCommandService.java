package com.agropapin.backend.organizationManagement.domain.model.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryItem;
import com.agropapin.backend.organizationManagement.domain.model.commands.*;

import java.util.Optional;

public interface InventoryCommandService {
    Optional<InventoryItem> handle(CreateInventoryItemCommand command);
    Optional<InventoryItem> handle(UpdateInventoryItemCommand command);
    Optional<InventoryItem> handle(IncreaseInventoryAmountCommand command);
    Optional<InventoryItem> handle(UseSupplyCommand command);
    Boolean handle(DeleteInventoryItemByIdCommand command);
}
