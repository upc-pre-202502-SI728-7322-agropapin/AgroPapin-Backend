package com.agropapin.backend.irrigationautomation.domain.model.commands;

import java.util.UUID;

/**
 * Command to manually activate irrigation for a specific actuator.
 * @param actuatorId The unique identifier of the actuator to activate.
 * @param minutes The duration in minutes for the irrigation.
 */
public record ActivateIrrigationCommand(UUID actuatorId, int minutes) {
    public ActivateIrrigationCommand {
        if (actuatorId == null) {
            throw new IllegalArgumentException("Actuator ID cannot be null.");
        }
        if (minutes <= 0) {
            throw new IllegalArgumentException("Minutes must be a positive integer.");
        }
    }
}
