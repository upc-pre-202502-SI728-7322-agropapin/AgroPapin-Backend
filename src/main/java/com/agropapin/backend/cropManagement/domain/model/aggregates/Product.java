package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.ProductType;
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
public class Product extends AuditableAbstractAggregateRoot<Product> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    @Embedded
    private Quantity quantity;

    @Column(nullable = false)
    private UUID plantingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    private Plot plot;

    public Product(String name, LocalDate applicationDate, ProductType type, Quantity quantity, UUID plantingId, Plot plot) {
        this.name = name;
        this.applicationDate = applicationDate;
        this.type = type;
        this.quantity = quantity;
        this.plantingId = plantingId;
        this.plot = plot;
    }

    public void update(String name, ProductType type, Quantity quantity) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }
}
