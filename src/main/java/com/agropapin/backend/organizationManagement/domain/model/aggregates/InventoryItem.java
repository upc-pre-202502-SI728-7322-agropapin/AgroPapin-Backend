package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.Quantity;
import com.agropapin.backend.organizationManagement.domain.model.valueobjects.SupplyCategory;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class InventoryItem extends AuditableAbstractAggregateRoot<InventoryItem> {

    @Column(nullable = false)
    private UUID cooperativeId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplyCategory category;

    @Embedded
    private Quantity quantity;

    public InventoryItem(UUID cooperativeId, String name, String description, SupplyCategory category, Quantity quantity) {
        this.cooperativeId = cooperativeId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public void updateDetails(String name, String description, SupplyCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void increaseQuantity(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to increase must be positive.");
        }
        this.quantity = new Quantity(this.quantity.getAmount() + amount, this.quantity.getUnit());
    }

    public void decreaseQuantity(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to decrease must be positive.");
        }
        if (this.quantity.getAmount() < amount) {
            throw new IllegalStateException("Not enough stock. Available: " + this.quantity.getAmount() + ", Requested: " + amount);
        }
        this.quantity = new Quantity(this.quantity.getAmount() - amount, this.quantity.getUnit());
    }
}
