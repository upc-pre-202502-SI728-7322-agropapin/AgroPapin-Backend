package com.agropapin.backend.devicemanagement.domain.services;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetAllActuatorsByPlotIdQuery;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetAllSensorsByPlotIdQuery;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    Optional<List<Sensor>> handle(GetAllSensorsByPlotIdQuery query);
    Optional<List<Actuator>> handle(GetAllActuatorsByPlotIdQuery query);
}
