package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.Quantity;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class InventoryUsageLog extends AuditableAbstractAggregateRoot<InventoryUsageLog> {

    @Column(nullable = false)
    private UUID inventoryItemId;

    @Embedded
    private Quantity quantityUsed;

    @Column(nullable = false)
    private LocalDate usageDate;

    private String purpose;

    public InventoryUsageLog(UUID inventoryItemId, Quantity quantityUsed, String purpose) {
        this.inventoryItemId = inventoryItemId;
        this.quantityUsed = quantityUsed;
        this.usageDate = LocalDate.now();
        this.purpose = purpose;
    }
}
