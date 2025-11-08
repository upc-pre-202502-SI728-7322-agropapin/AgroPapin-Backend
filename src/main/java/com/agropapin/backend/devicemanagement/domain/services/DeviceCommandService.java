package com.agropapin.backend.devicemanagement.domain.services;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateActuatorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.CreateSensorCommand;

import java.util.UUID;

public interface DeviceCommandService {
    UUID handle(CreateSensorCommand command);
    UUID handle(CreateActuatorCommand command);
}
