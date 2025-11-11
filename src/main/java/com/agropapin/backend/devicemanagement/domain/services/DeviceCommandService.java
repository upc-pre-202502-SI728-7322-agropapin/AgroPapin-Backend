package com.agropapin.backend.devicemanagement.domain.services;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import com.agropapin.backend.devicemanagement.domain.model.commands.CreateActuatorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.CreateSensorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.UpdateActuatorStatusCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.UpdateSensorStatusCommand;

import java.util.Optional;
import java.util.UUID;

public interface DeviceCommandService {
    UUID handle(CreateSensorCommand command);
    UUID handle(CreateActuatorCommand command);
    Optional<Actuator> handle(UpdateActuatorStatusCommand command);
    Optional<Sensor> handle(UpdateSensorStatusCommand command);
}
