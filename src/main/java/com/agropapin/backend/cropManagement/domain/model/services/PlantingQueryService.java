package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlantingByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlantingByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PlantingQueryService {
    List<Planting> handle(GetAllPlantingByPlotIdQuery query);
    Optional<Planting> handle(GetPlantingByIdQuery query);
}
