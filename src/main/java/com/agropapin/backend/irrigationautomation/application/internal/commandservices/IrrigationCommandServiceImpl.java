package com.agropapin.backend.irrigationautomation.application.internal.commandservices;

import com.agropapin.backend.irrigationautomation.domain.model.commands.ActivateIrrigationCommand;
import com.agropapin.backend.irrigationautomation.domain.model.gateways.IrrigationMqttGateway;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IrrigationCommandServiceImpl implements IrrigationCommandService {

    private static final Logger log = LoggerFactory.getLogger(IrrigationCommandServiceImpl.class);
    private final IrrigationMqttGateway irrigationMqttGateway;

    public IrrigationCommandServiceImpl(IrrigationMqttGateway irrigationMqttGateway) {
        this.irrigationMqttGateway = irrigationMqttGateway;
    }

    @Override
    public void handle(ActivateIrrigationCommand command) {
        log.info("Handling command to activate irrigation for actuator {} for {} minutes.", command.actuatorId(), command.minutes());
        
        // Here you could add more complex business logic, for example:
        // - Check if the actuator is currently online (via another BC).
        // - Check if the plot is already being irrigated.
        // - Validate if the user has permission to activate this actuator.

        // For now, we directly delegate to the gateway.
        irrigationMqttGateway.publishIrrigationCommand(command.actuatorId(), command.minutes());

        log.info("Successfully published irrigation command for actuator {}.", command.actuatorId());
    }
}
