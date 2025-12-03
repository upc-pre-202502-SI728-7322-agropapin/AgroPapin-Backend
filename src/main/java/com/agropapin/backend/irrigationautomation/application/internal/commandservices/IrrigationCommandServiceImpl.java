package com.agropapin.backend.irrigationautomation.application.internal.commandservices;

import com.agropapin.backend.devicemanagement.interfaces.acl.DeviceManagementFacade;
import com.agropapin.backend.irrigationautomation.domain.model.commands.ActivateIrrigationCommand;
import com.agropapin.backend.irrigationautomation.domain.model.commands.CreateIrrigationLogCommand;
import com.agropapin.backend.irrigationautomation.domain.model.gateways.IrrigationMqttGateway;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationCommandService;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationLogCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class IrrigationCommandServiceImpl implements IrrigationCommandService {

    private static final Logger log = LoggerFactory.getLogger(IrrigationCommandServiceImpl.class);
    private final IrrigationMqttGateway irrigationMqttGateway;
    private final IrrigationLogCommandService irrigationLogCommandService;
    private final DeviceManagementFacade deviceManagementFacade;

    public IrrigationCommandServiceImpl(IrrigationMqttGateway irrigationMqttGateway, IrrigationLogCommandService irrigationLogCommandService, DeviceManagementFacade deviceManagementFacade) {
        this.irrigationMqttGateway = irrigationMqttGateway;
        this.irrigationLogCommandService = irrigationLogCommandService;
        this.deviceManagementFacade = deviceManagementFacade;
    }

    @Override
    @Transactional
    public void handle(ActivateIrrigationCommand command) {
        log.info("Handling command to manually activate irrigation for actuator {} for {} minutes.", command.actuatorId(), command.minutes());

        irrigationMqttGateway.publishIrrigationCommand(command.actuatorId(), command.minutes());
        log.info("Successfully published irrigation command for actuator {}.", command.actuatorId());

        UUID plotId = deviceManagementFacade.getPlotIdByActuatorId(command.actuatorId());
        var logCommand = new CreateIrrigationLogCommand(
                plotId,
                "IRRIGATE",
                "MANUAL_ACTIVATION",
                -1, // No humidity reading for manual activation
                -1  // No threshold for manual activation
        );
        irrigationLogCommandService.handle(logCommand);
        log.info("Manual activation logged for actuator {}.", command.actuatorId());
    }
}
