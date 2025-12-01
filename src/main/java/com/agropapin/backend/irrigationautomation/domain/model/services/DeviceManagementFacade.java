package com.agropapin.backend.irrigationautomation.domain.model.services;

import java.util.UUID;

/**
 * Facade interface defining the contract for what IrrigationAutomation needs from the DeviceManagement BC.
 * This acts as an Anti-Corruption Layer (ACL).
 */
public interface DeviceManagementFacade {

    /**
     * Sends a command to activate a specific actuator.
     * @param actuatorId The ID of the actuator to activate.
     * @param durationMinutes The duration in minutes for the activation.
     */
    void activateActuator(UUID actuatorId, int durationMinutes);
}
