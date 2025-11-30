package com.agropapin.backend.devicemanagement.domain.services;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import com.agropapin.backend.devicemanagement.domain.model.commands.*;

import java.util.Optional;
import java.util.UUID;

public interface DeviceCommandService {
    UUID handle(CreateSensorCommand command);
    UUID handle(CreateActuatorCommand command);
    Optional<Actuator> handle(UpdateActuatorStatusCommand command);
    Optional<Sensor> handle(UpdateSensorStatusCommand command);
    Boolean handle(DeleteActuatorCommand actuatorId);
    Boolean handle(DeleteSensorCommand sensorId);
}
