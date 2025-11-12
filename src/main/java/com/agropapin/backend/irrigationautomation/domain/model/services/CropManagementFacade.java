package com.agropapin.backend.irrigationautomation.domain.model.services;

import java.util.UUID;

/**
 * Facade interface defining the contract for what IrrigationAutomation needs from the CropManagement BC.
 * This acts as an Anti-Corruption Layer (ACL).
 * The implementation of this interface will live in the CropManagement BC.
 */
public interface CropManagementFacade {
    
    /**
     * Gets the configured humidity threshold for a specific plot.
     * @param plotId The ID of the plot.
     * @return The humidity threshold (e.g., 30.0 for 30%).
     */
    double getHumidityThresholdForPlot(UUID plotId);

    /**
     * Gets the actuator ID associated with a specific plot.
     * @param plotId The ID of the plot.
     * @return The ID of the actuator responsible for irrigating the plot.
     */
    UUID getActuatorIdForPlot(UUID plotId);
}
