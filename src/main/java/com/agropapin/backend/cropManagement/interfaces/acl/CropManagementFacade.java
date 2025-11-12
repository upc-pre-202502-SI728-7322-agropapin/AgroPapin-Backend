package com.agropapin.backend.cropManagement.interfaces.acl;

import com.agropapin.backend.cropManagement.application.internal.queryservices.PlantingQueryServiceImpl;
import com.agropapin.backend.cropManagement.domain.model.queries.GetIrrigationRulesByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.IrrigationRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CropManagementFacade {
    private final PlantingQueryServiceImpl plantingQueryService;

    public CropManagementFacade(PlantingQueryServiceImpl plantingQueryService) {
        this.plantingQueryService = plantingQueryService;
    }

    public List<IrrigationRule> getIrrigationRuleByPlotId(UUID plotId) {
        var getIrrigationRulesByPlotIdQuery = new GetIrrigationRulesByPlotIdQuery(plotId);

        var irrigationRules = plantingQueryService.handle(getIrrigationRulesByPlotIdQuery);

        return irrigationRules.orElse(null);
    }
}
