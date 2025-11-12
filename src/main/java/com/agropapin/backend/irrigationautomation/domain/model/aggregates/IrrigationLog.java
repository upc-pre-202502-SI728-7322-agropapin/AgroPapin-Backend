package com.agropapin.backend.irrigationautomation.domain.model.aggregates;

import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class IrrigationLog extends AuditableAbstractAggregateRoot<IrrigationLog> {

    private UUID plotId;
    private Instant decisionTimestamp;
    private String decision;
    private String reason;
    private double humidityReading;
    private double humidityThreshold;

    public IrrigationLog(UUID plotId, String decision, String reason, double humidityReading, double humidityThreshold) {
        this.plotId = plotId;
        this.decisionTimestamp = Instant.now();
        this.decision = decision;
        this.reason = reason;
        this.humidityReading = humidityReading;
        this.humidityThreshold = humidityThreshold;
    }
}
