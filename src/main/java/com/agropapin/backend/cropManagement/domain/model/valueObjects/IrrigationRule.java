package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import com.agropapin.backend.cropManagement.domain.model.enums.MetricTrigger;
import com.agropapin.backend.cropManagement.domain.model.enums.Operator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class IrrigationRule {

    @Enumerated(EnumType.STRING)
    private MetricTrigger triggerMetric;

    @Enumerated(EnumType.STRING)
    private Operator operator;

    private double thresholdValue;

    @Column(name = "recommended_action", length = 50)
    private String recommendedAction;

    private int recommendedDurationMinutes;

}
