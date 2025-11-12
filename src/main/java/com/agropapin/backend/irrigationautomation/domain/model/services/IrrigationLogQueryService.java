package com.agropapin.backend.irrigationautomation.domain.model.services;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import com.agropapin.backend.irrigationautomation.domain.model.queries.GetIrrigationLogsByPlotIdQuery;

import java.util.List;

public interface IrrigationLogQueryService {
    List<IrrigationLog> handle(GetIrrigationLogsByPlotIdQuery query);
}
