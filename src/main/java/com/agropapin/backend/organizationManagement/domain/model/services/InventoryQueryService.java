package com.agropapin.backend.organizationManagement.domain.model.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryItem;
import com.agropapin.backend.organizationManagement.domain.model.projections.UsageStatisticProjection;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetInventoryByCooperativeIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetUsageStatisticsQuery;

import java.util.List;

public interface InventoryQueryService {
    List<InventoryItem> handle(GetInventoryByCooperativeIdQuery query);
    List<UsageStatisticProjection> handle(GetUsageStatisticsQuery query);
}
