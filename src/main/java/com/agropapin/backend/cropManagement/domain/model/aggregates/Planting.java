package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.CropStatus;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.PlotStatus;
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

    private Date plantingDate;

    @Column(updatable = true)
    private Date estimatedHarvestDate;

    @Column(updatable = true)
    private Date actualHarvestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Status is mandatory")
    private CropStatus status;

    @Column(name = "plot_id", nullable = false, columnDefinition = "BINARY(16)")
    @NotNull(message = "Plot is mandatory")
    private UUID plotId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crop_type_id", nullable = false)
    @NotNull(message = "Crop type is mandatory")
    private CropType cropType;


    public Planting(Date plantingDate, UUID plotId, CropType cropType) {
        this.plantingDate = plantingDate;
        this.plotId = plotId;
        this.status = CropStatus.GROWING;
        this.cropType = cropType;
    }

    public void update(Date plantingDate, Date harvestDate, UUID plotId) {
        this.plantingDate = plantingDate;
        this.actualHarvestDate = harvestDate;
        this.plotId = plotId;
    }

    public void updateStatus(CropStatus newStatus) {
        if (this.status == newStatus) {
            return;
        }

        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("Invalid status transition from %s to %s", this.status, newStatus)
            );
        }

        CropStatus oldStatus = this.status;
        this.status = newStatus;
    }

    public boolean isEmpty() {
        return this.status == CropStatus.GROWING;
    }

    public boolean isPlanted() {
        return this.status == CropStatus.HARVESTED;
    }

    public boolean isHarvested() {
        return this.status != CropStatus.FAILED;
    }
}
