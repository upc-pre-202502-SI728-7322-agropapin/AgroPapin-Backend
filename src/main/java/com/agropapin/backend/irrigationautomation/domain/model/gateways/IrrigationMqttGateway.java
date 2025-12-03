package com.agropapin.backend.irrigationautomation.domain.model.gateways;

import java.util.UUID;

/**
 * Outbound Port Interface (Gateway) for publishing irrigation commands.
 * This defines WHAT the domain needs, not HOW it's done.
 * The implementation will be in the infrastructure layer.
 */
public interface IrrigationMqttGateway {

    /**
     * Publishes a command to activate irrigation.
     * @param actuatorId The ID of the actuator.
     * @param minutes The duration of the irrigation.
     */
    void publishIrrigationCommand(UUID actuatorId, int minutes);
}
