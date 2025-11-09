package com.agropapin.backend.devicemanagement.application.internal.commandservices;

import com.agropapin.backend.devicemanagement.domain.model.aggregates.Actuator;
import com.agropapin.backend.devicemanagement.domain.model.aggregates.Sensor;
import com.agropapin.backend.devicemanagement.domain.model.commands.CreateActuatorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.CreateSensorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.UpdateActuatorStatusCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.UpdateSensorStatusCommand;
import com.agropapin.backend.devicemanagement.domain.model.valueobjects.DeviceModel;
import com.agropapin.backend.devicemanagement.domain.services.DeviceCommandService;
import com.agropapin.backend.devicemanagement.infrastructure.persistence.jpa.repositories.ActuatorRepository;
import com.agropapin.backend.devicemanagement.infrastructure.persistence.jpa.repositories.SensorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final SensorRepository sensorRepository;
    private final ActuatorRepository actuatorRepository;

    public DeviceCommandServiceImpl(SensorRepository sensorRepository, ActuatorRepository actuatorRepository) {
        this.sensorRepository = sensorRepository;
        this.actuatorRepository = actuatorRepository;
    }

    @Override
    public UUID handle(CreateSensorCommand command) {
        var deviceModel = new DeviceModel(command.model(), command.version());
        var sensor = new Sensor(command.serialNumber(), command.plotId(), deviceModel);
        sensorRepository.save(sensor);
        return sensor.getId();
    }

    @Override
    public UUID handle(CreateActuatorCommand command) {
        var deviceModel = new DeviceModel(command.model(), command.version());
        var actuator = new Actuator(command.serialNumber(), command.plotId(), deviceModel, command.actuatorType());
        actuatorRepository.save(actuator);
        return actuator.getId();
    }

    @Override
    @Transactional
    public Optional<Actuator> handle(UpdateActuatorStatusCommand command) {
        var actuator = actuatorRepository.findActuatorByIdAndPlotId(command.actuatorId(), command.plotId());

        actuator.get().updateStatus(command.status());

        var savedActuator = actuatorRepository.save(actuator.get());

        return Optional.of(savedActuator);
    }

    @Override
    public Optional<Sensor> handle(UpdateSensorStatusCommand command) {
        var sensor = sensorRepository.findSensorByIdAndPlotId(command.actuatorId(), command.plotId());

        sensor.get().updateStatus(command.status());

        var savedSensor = sensorRepository.save(sensor.get());

        return Optional.of(savedSensor);
    }
}
