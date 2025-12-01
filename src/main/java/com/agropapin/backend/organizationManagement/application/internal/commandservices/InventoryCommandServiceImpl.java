package com.agropapin.backend.organizationManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.Quantity;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryItem;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryUsageLog;
import com.agropapin.backend.organizationManagement.domain.model.commands.*;
import com.agropapin.backend.organizationManagement.domain.model.services.InventoryCommandService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.InventoryItemRepository;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.InventoryUsageLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryCommandServiceImpl implements InventoryCommandService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryUsageLogRepository inventoryUsageLogRepository;

    public InventoryCommandServiceImpl(InventoryItemRepository inventoryItemRepository, InventoryUsageLogRepository inventoryUsageLogRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryUsageLogRepository = inventoryUsageLogRepository;
    }

    @Override
    public Optional<InventoryItem> handle(CreateInventoryItemCommand command) {
        var item = new InventoryItem(
                command.cooperativeId(),
                command.name(),
                command.description(),
                command.category(),
                new Quantity(command.initialAmount(), command.unit())
        );
        inventoryItemRepository.save(item);
        return Optional.of(item);
    }

    @Override
    public Optional<InventoryItem> handle(UpdateInventoryItemCommand command) {
        return inventoryItemRepository.findById(command.itemId()).map(item -> {
            item.updateDetails(command.name(), command.description(), command.category());
            inventoryItemRepository.save(item);
            return item;
        });
    }

    @Override
    public Optional<InventoryItem> handle(IncreaseInventoryAmountCommand command) {
        return inventoryItemRepository.findById(command.itemId()).map(item -> {
            item.increaseQuantity(command.amount());
            inventoryItemRepository.save(item);
            return item;
        });
    }

    @Override
    @Transactional
    public Optional<InventoryItem> handle(UseSupplyCommand command) {
        return inventoryItemRepository.findById(command.itemId()).map(item -> {
            item.decreaseQuantity(command.amountToUse());
            inventoryItemRepository.save(item);

            var usageLog = new InventoryUsageLog(
                    item.getId(),
                    new Quantity(command.amountToUse(), item.getQuantity().getUnit()),
                    command.purpose()
            );
            inventoryUsageLogRepository.save(usageLog);

            return item;
        });
    }

    @Override
    public Boolean handle(DeleteInventoryItemByIdCommand command) {
        inventoryItemRepository.deleteById(command.inventoryItemId());
        return true;
    }
}
