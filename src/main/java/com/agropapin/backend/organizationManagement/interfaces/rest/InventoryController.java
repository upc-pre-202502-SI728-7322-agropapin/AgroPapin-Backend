package com.agropapin.backend.organizationManagement.interfaces.rest;

import com.agropapin.backend.organizationManagement.domain.model.commands.*;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetInventoryByCooperativeIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetUsageStatisticsQuery;
import com.agropapin.backend.organizationManagement.domain.model.services.InventoryCommandService;
import com.agropapin.backend.organizationManagement.domain.model.services.InventoryQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.*;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.InventoryItemResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/cooperative/{cooperativeId}/inventory", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Inventory", description = "Inventory and Supply Management Endpoints")
public class InventoryController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    public InventoryController(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    @PostMapping
    public ResponseEntity<InventoryItemResource> createInventoryItem(@PathVariable UUID cooperativeId, @RequestBody CreateInventoryItemResource resource) {
        var command = new CreateInventoryItemCommand(
                cooperativeId,
                resource.name(),
                resource.description(),
                resource.category(),
                resource.initialAmount(),
                resource.unit()
        );
        var item = inventoryCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Error creating inventory item"));
        
        var itemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(item);
        return new ResponseEntity<>(itemResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryItemResource>> getInventory(@PathVariable UUID cooperativeId) {
        var query = new GetInventoryByCooperativeIdQuery(cooperativeId);
        var items = inventoryQueryService.handle(query);
        var itemResources = items.stream()
                .map(InventoryItemResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemResources);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<InventoryItemResource> updateInventoryItem(@PathVariable UUID cooperativeId, @PathVariable UUID itemId, @RequestBody UpdateInventoryItemResource resource) {
        var command = new UpdateInventoryItemCommand(
                itemId,
                resource.name(),
                resource.description(),
                resource.category()
        );
        var item = inventoryCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Item not found or error updating"));

        var itemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(item);
        return ResponseEntity.ok(itemResource);
    }

    @PatchMapping("/{itemId}/increase-amount")
    public ResponseEntity<InventoryItemResource> increaseInventoryAmount(
            @PathVariable UUID cooperativeId,
            @PathVariable UUID itemId,
            @RequestBody IncreaseInventoryAmountResource resource) {

        var command = new IncreaseInventoryAmountCommand(
                itemId,
                resource.amount()
        );

        var item = inventoryCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Item not found or error updating amount"));

        var itemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(item);
        return ResponseEntity.ok(itemResource);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable UUID cooperativeId, @PathVariable UUID itemId) {
        inventoryCommandService.handle(new DeleteInventoryItemByIdCommand(itemId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{itemId}/use")
    public ResponseEntity<InventoryItemResource> useSupply(@PathVariable UUID cooperativeId, @PathVariable UUID itemId, @RequestBody UseSupplyResource resource) {
        var command = new UseSupplyCommand(
                itemId,
                resource.amountToUse(),
                resource.purpose()
        );
        var item = inventoryCommandService.handle(command)
                .orElseThrow(() -> new IllegalStateException("Failed to use supply. Check stock or item ID."));

        var itemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(item);
        return ResponseEntity.ok(itemResource);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<UsageStatisticResource>> getUsageStatistics(@PathVariable UUID cooperativeId) {
        var query = new GetUsageStatisticsQuery(cooperativeId);
        var stats = inventoryQueryService.handle(query);
        var resources = stats.stream()
                .map(s -> new UsageStatisticResource(s.itemId(), s.itemName(), s.totalAmountUsed(), s.unit()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}
