package com.agropapin.backend.irrigationautomation.application.internal.services;

import com.agropapin.backend.irrigationautomation.domain.model.commands.CreateIrrigationLogCommand;
import com.agropapin.backend.irrigationautomation.domain.model.events.IrrigationNeededEvent;
import com.agropapin.backend.irrigationautomation.domain.model.gateways.IrrigationMqttGateway;
import com.agropapin.backend.irrigationautomation.domain.model.services.CropManagementFacade;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationLogCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IrrigationActionService {

    private static final Logger log = LoggerFactory.getLogger(IrrigationActionService.class);
    private final IrrigationMqttGateway irrigationMqttGateway;
    private final IrrigationLogCommandService irrigationLogCommandService;
    private final CropManagementFacade cropManagementFacade;

    public IrrigationActionService(IrrigationMqttGateway irrigationMqttGateway, IrrigationLogCommandService irrigationLogCommandService, CropManagementFacade cropManagementFacade) {
        this.irrigationMqttGateway = irrigationMqttGateway;
        this.irrigationLogCommandService = irrigationLogCommandService;
        this.cropManagementFacade = cropManagementFacade;
    }

    @EventListener
    @Transactional
    public void onIrrigationNeeded(IrrigationNeededEvent event) {
        log.info("ACTION_SERVICE: Received IrrigationNeededEvent for plot {}.", event.plotId());

        var policy = cropManagementFacade.getIrrigationPolicyForPlot(event.plotId());
        if (policy.actuatorId() == null) {
            log.error("ACTION_SERVICE: Cannot irrigate plot {}. No actuatorId found in policy.", event.plotId());
            return;
        }

        // 2. Send command to the device via MQTT
        irrigationMqttGateway.publishIrrigationCommand(policy.actuatorId(), event.durationMinutes());
        log.info("ACTION_SERVICE: Published MQTT command for actuator {} (plot {}).", policy.actuatorId(), event.plotId());

        // 3. Log the automatic action
        var logCommand = new CreateIrrigationLogCommand(
                event.plotId(),
                "IRRIGATE",
                "AUTOMATIC_THRESHOLD_CROSSED",
                event.currentHumidity(),
                event.humidityThreshold()
        );
        irrigationLogCommandService.handle(logCommand);
        log.info("ACTION_SERVICE: Logged automatic irrigation for plot {}.", event.plotId());
    }
}
