package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Control extends AuditableAbstractAggregateRoot<Control> {

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String stateLeaves;

    @Column(nullable = false)
    private String stateStem;

    @Column(nullable = false)
    private String soilMoisture;

    @Column(nullable = false)
    private UUID plantingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    private Plot plot;

    public Control(LocalDate date, String stateLeaves, String stateStem, String soilMoisture, UUID plantingId, Plot plot) {
        this.date = date;
        this.stateLeaves = stateLeaves;
        this.stateStem = stateStem;
        this.soilMoisture = soilMoisture;
        this.plantingId = plantingId;
        this.plot = plot;
    }

    public void update(LocalDate date, String stateLeaves, String stateStem, String soilMoisture) {
        this.date = date;
        this.stateLeaves = stateLeaves;
        this.stateStem = stateStem;
        this.soilMoisture = soilMoisture;
    }
}
