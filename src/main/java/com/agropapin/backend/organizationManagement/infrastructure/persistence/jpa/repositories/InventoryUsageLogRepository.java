package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryUsageLog;
import com.agropapin.backend.organizationManagement.domain.model.projections.UsageStatisticProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryUsageLogRepository extends JpaRepository<InventoryUsageLog, UUID> {

    @Query("""
            SELECT new com.agropapin.backend.organizationManagement.domain.model.projections.UsageStatisticProjection(
                item.id,
                item.name,
                SUM(log.quantityUsed.amount),
                item.quantity.unit
            )
            FROM InventoryUsageLog log
            JOIN InventoryItem item ON log.inventoryItemId = item.id
            WHERE item.cooperativeId = :cooperativeId
            GROUP BY item.id, item.name, item.quantity.unit
            ORDER BY SUM(log.quantityUsed.amount) DESC
            """)
    List<UsageStatisticProjection> getUsageStatisticsByCooperative(UUID cooperativeId);
}
