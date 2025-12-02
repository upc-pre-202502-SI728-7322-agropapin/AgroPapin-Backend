package com.agropapin.backend.cropManagement.interfaces.acl;

import com.agropapin.backend.cropManagement.application.internal.queryservices.PlantingQueryServiceImpl;
import com.agropapin.backend.cropManagement.application.internal.queryservices.PlotQueryServiceImpl;
import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.domain.model.queries.GetActivePlantingByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlotByFieldIdQuery;
import com.agropapin.backend.cropManagement.interfaces.acl.resources.PlotSummaryForAgent;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PlotManagementFacade {
    private final PlotQueryServiceImpl plotQueryService;
    private final PlantingQueryServiceImpl plantingQueryService;

    public PlotManagementFacade(PlotQueryServiceImpl plotQueryService, PlantingQueryServiceImpl plantingQueryService) {
        this.plotQueryService = plotQueryService;
        this.plantingQueryService = plantingQueryService;
    }

    public List<PlotSummaryForAgent> getPlotsSummaryByFieldId(UUID fieldId) {
        var plots = plotQueryService.handle(new GetAllPlotByFieldIdQuery(fieldId))
                .orElse(Collections.emptyList());

        // 2. Transformar a resumen
        return plots.stream().map(this::mapToPlotSummary).toList();
    }

    private PlotSummaryForAgent mapToPlotSummary(Plot plot) {
        var activePlantingOpt = plantingQueryService.handle(
                new GetActivePlantingByPlotIdQuery(plot.getId())
        );

        String currentCrop = null;
        String plantingDate = null;

        if (activePlantingOpt.isPresent()) {
            Planting planting = activePlantingOpt.get();
            currentCrop = planting.getCropType().getName();
            plantingDate = planting.getPlantingDate().toString();
        }

        return new PlotSummaryForAgent(
                plot.getId().toString(),
                plot.getPlotName(),
                plot.getStatus().name(),
                currentCrop,
                plantingDate,
                plot.getArea() + " m2"
        );
    }
}
