package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlotByFieldIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlotByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PlotQueryService {
    Optional<Plot> handle(GetPlotByIdQuery getPlotByIdQuery);
    Optional<List<Plot>> handle(GetAllPlotByFieldIdQuery getAllPlotByFieldIdQuery);
}
