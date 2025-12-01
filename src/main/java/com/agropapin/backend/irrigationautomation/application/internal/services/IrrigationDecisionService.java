package com.agropapin.backend.irrigationautomation.application.internal.services;

import com.agropapin.backend.irrigationautomation.domain.model.events.IrrigationNeededEvent;
import com.agropapin.backend.irrigationautomation.domain.model.services.CropManagementFacade;
import com.agropapin.backend.telemetryingestion.domain.model.events.TelemetryReadingReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class IrrigationDecisionService {

    private static final Logger log = LoggerFactory.getLogger(IrrigationDecisionService.class);
    private final CropManagementFacade cropManagementFacade;
    private final ApplicationEventPublisher eventPublisher;

    public IrrigationDecisionService(CropManagementFacade cropManagementFacade, ApplicationEventPublisher eventPublisher) {
        this.cropManagementFacade = cropManagementFacade;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void onTelemetryReadingReceived(TelemetryReadingReceivedEvent event) {
        log.info("DECISION_SERVICE: Received telemetry for plot {}: humidity = {}", event.plotId(), event.humidity());

        var policy = cropManagementFacade.getIrrigationPolicyForPlot(event.plotId());
        if (!policy.isPolicyDefined()) {
            log.warn("DECISION_SERVICE: No irrigation policy defined for plot {}. Skipping.", event.plotId());
            return;
        }
        log.debug("DECISION_SERVICE: Policy for plot {}: Threshold={}, Duration={}", event.plotId(), policy.humidityThreshold(), policy.defaultDurationMinutes());

        if (event.humidity() < policy.humidityThreshold()) {
            log.info("DECISION_SERVICE: Decision for plot {}: IRRIGATION NEEDED. Humidity ({}) is below threshold ({}).",
                    event.plotId(), event.humidity(), policy.humidityThreshold());

            var irrigationEvent = new IrrigationNeededEvent(
                    event.plotId(),
                    event.humidity(),
                    policy.humidityThreshold(),
                    policy.defaultDurationMinutes()
            );
            eventPublisher.publishEvent(irrigationEvent);

        } else {
            log.info("DECISION_SERVICE: Decision for plot {}: NO IRRIGATION NEEDED. Humidity ({}) is normal.",
                    event.plotId(), event.humidity());
        }
    }
}
