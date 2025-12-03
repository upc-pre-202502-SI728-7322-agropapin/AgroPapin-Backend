package com.agropapin.backend.irrigationautomation.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.IrrigationRule;
import com.agropapin.backend.irrigationautomation.domain.model.valueobjects.IrrigationPolicy;

import java.util.List;
import java.util.UUID;

/**
 * Facade providing a simplified view of the CropManagement Bounded Context
 * for the needs of IrrigationAutomation.
 */
public interface CropManagementFacade {
    IrrigationPolicy getIrrigationPolicyForPlot(UUID plotId);
    List<IrrigationRule> getIrrigationRuleByPlotId(UUID plotId);
    Planting getCropInfo(UUID plotId);
}
