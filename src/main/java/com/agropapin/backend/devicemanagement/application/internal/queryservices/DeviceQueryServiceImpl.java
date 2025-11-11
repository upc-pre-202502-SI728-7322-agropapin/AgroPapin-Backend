package com.agropapin.backend.devicemanagement.application.internal.queryservices;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetAllActuatorsByPlotIdQuery;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetAllSensorsByPlotIdQuery;
import com.agropapin.backend.devicemanagement.domain.services.DeviceQueryService;
import com.agropapin.backend.devicemanagement.infrastructure.persistence.jpa.repositories.ActuatorRepository;
import com.agropapin.backend.devicemanagement.infrastructure.persistence.jpa.repositories.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {

    private final SensorRepository sensorRepository;
    private final ActuatorRepository actuatorRepository;

    public DeviceQueryServiceImpl(SensorRepository sensorRepository, ActuatorRepository actuatorRepository) {
        this.sensorRepository = sensorRepository;
        this.actuatorRepository = actuatorRepository;
    }

    @Override
    public Optional<List<Sensor>> handle(GetAllSensorsByPlotIdQuery query) {
        List<Sensor> sensors = sensorRepository.findAll();
        return sensors.isEmpty() ? Optional.empty() : Optional.of(sensors);
    }

    @Override
    public Optional<List<Actuator>> handle(GetAllActuatorsByPlotIdQuery query) {
        List<Actuator> actuators = actuatorRepository.findAll();
        return actuators.isEmpty() ? Optional.empty() : Optional.of(actuators);
    }
}
