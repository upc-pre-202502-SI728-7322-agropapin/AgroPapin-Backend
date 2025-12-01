package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.IrrigationRule;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrrigationPolicy extends AuditableAbstractAggregateRoot<IrrigationPolicy> {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "crop_name", nullable = false, length = 100)
    private String cropName;

    @Column(name = "crop_variety", length = 100)
    private String cropVariety;

    @Column(name = "crop_type_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID cropTypeId;

    @ElementCollection
    @CollectionTable(name = "irrigation_policy_rules",
            joinColumns = @JoinColumn(name = "policy_id"))
    private List<IrrigationRule> rules;
}
