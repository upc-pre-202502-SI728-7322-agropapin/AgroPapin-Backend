package com.agropapin.backend.cropManagement.infraestructure.config.dto;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.IrrigationRule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IrrigationPolicyDTO {
    private UUID id;
    private String name;
    private String cropName;
    private String cropVariety;
    private UUID cropTypeId;
    private List<IrrigationRule> rules;
}
