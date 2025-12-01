package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Control;
import com.agropapin.backend.cropManagement.domain.model.queries.GetControlsByPlantingIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetControlsByPlotIdQuery;

import java.util.List;

public interface ControlQueryService {
    List<Control> handle(GetControlsByPlotIdQuery query);
    List<Control> handle(GetControlsByPlantingIdQuery query);
}
