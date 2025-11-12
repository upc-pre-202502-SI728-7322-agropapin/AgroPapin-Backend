package com.agropapin.backend.irrigationautomation.application.internal.services;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import com.agropapin.backend.irrigationautomation.domain.model.repositories.IrrigationLogRepository;
import com.agropapin.backend.telemetryingestion.domain.model.events.TelemetryReadingReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class IrrigationDecisionService {

    private static final Logger log = LoggerFactory.getLogger(IrrigationDecisionService.class);
    private final IrrigationLogRepository irrigationLogRepository;
    // Facades for other BCs will be injected here
    // private final CropManagementFacade cropManagementFacade;
    // private final DeviceManagementFacade deviceManagementFacade;

    public IrrigationDecisionService(IrrigationLogRepository irrigationLogRepository) {
        this.irrigationLogRepository = irrigationLogRepository;
    }

    @EventListener
    public void onTelemetryReadingReceived(TelemetryReadingReceivedEvent event) {
        log.info("Received telemetry event for plot {}: humidity = {}", event.plotId(), event.humidity());

        // **THIS IS THE CORE LOGIC**
        // In a real scenario, you would implement the following steps:

        // 1. GET IRRIGATION POLICY
        // double humidityThreshold = cropManagementFacade.getHumidityThresholdForPlot(event.plotId());
        // log.debug("Retrieved humidity threshold for plot {}: {}", event.plotId(), humidityThreshold);
        double humidityThreshold = 30.0; // Hardcoded for now

        // 2. MAKE DECISION
        if (event.humidity() < humidityThreshold) {
            log.info("Decision for plot {}: IRRIGATE. Humidity ({}) is below threshold ({}).",
                    event.plotId(), event.humidity(), humidityThreshold);

            // 3. SEND COMMAND TO DEVICE MANAGEMENT
            // UUID actuatorId = cropManagementFacade.getActuatorIdForPlot(event.plotId());
            // deviceManagementFacade.activateActuator(actuatorId, 5); // Activate for 5 minutes
            // log.info("Sent command to activate actuator for plot {}", event.plotId());

            // 4. LOG THE ACTION
            var logEntry = new IrrigationLog(event.plotId(), "IRRIGATE", "HUMIDITY_BELOW_THRESHOLD", event.humidity(), humidityThreshold);
            irrigationLogRepository.save(logEntry);

        } else {
            log.info("Decision for plot {}: DO_NOT_IRRIGATE. Humidity ({}) is normal.",
                    event.plotId(), event.humidity());
            
            // 4. LOG THE (NON-)ACTION
            var logEntry = new IrrigationLog(event.plotId(), "DO_NOT_IRRIGATE", "HUMIDITY_NORMAL", event.humidity(), humidityThreshold);
            irrigationLogRepository.save(logEntry);
        }
    }
}
