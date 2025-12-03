package com.agropapin.backend.organizationManagement.application.internal.queryservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryItem;
import com.agropapin.backend.organizationManagement.domain.model.projections.UsageStatisticProjection;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetInventoryByCooperativeIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetUsageStatisticsQuery;
import com.agropapin.backend.organizationManagement.domain.model.services.InventoryQueryService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.InventoryItemRepository;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.InventoryUsageLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryUsageLogRepository inventoryUsageLogRepository;

    public InventoryQueryServiceImpl(InventoryItemRepository inventoryItemRepository, InventoryUsageLogRepository inventoryUsageLogRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryUsageLogRepository = inventoryUsageLogRepository;
    }

    @Override
    public List<InventoryItem> handle(GetInventoryByCooperativeIdQuery query) {
        return inventoryItemRepository.findByCooperativeId(query.cooperativeId());
    }

    @Override
    public List<UsageStatisticProjection> handle(GetUsageStatisticsQuery query) {
        return inventoryUsageLogRepository.getUsageStatisticsByCooperative(query.cooperativeId());
    }
}
