package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.CropStatus;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planting extends AuditableAbstractAggregateRoot<Planting> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    @NotNull(message = "Plot is mandatory")
    private Plot plot;

    @Column(nullable = false)
    @NotNull(message = "CropType is mandatory")
    private UUID cropTypeId;

    @Column(nullable = false, updatable = false)
    private Date plantingDate;

    @Column(nullable = false, updatable = false)
    private Date estimatedHarvestDate;

    @Column(nullable = false, updatable = false)
    private Date actualHarvestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Status is mandatory")
    private CropStatus state;
}
