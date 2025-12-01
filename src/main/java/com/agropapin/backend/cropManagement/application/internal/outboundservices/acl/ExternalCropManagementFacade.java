package com.agropapin.backend.cropManagement.application.internal.outboundservices.acl;

import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlotRepository;
import com.agropapin.backend.irrigationautomation.domain.model.services.CropManagementFacade;
import com.agropapin.backend.irrigationautomation.domain.model.valueobjects.IrrigationPolicy;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Anti-Corruption Layer (ACL) implementation.
 * This class adapts the internal model of CropManagement to the contract
 * required by the IrrigationAutomation BC.
 */
@Service
public class ExternalCropManagementFacade implements CropManagementFacade {

    private final PlotRepository plotRepository;
    // We would also inject PlantingRepository, CropRepository, etc.

    public ExternalCropManagementFacade(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    @Override
    public IrrigationPolicy getIrrigationPolicyForPlot(UUID plotId) {
        // This is a simplified implementation. A real one would be more complex:
        // 1. Find Plot by plotId.
        // 2. Find the *active* Planting for that Plot.
        // 3. From the Planting, get the CropId.
        // 4. From the Crop, get its irrigation parameters (threshold, duration).
        // 5. From the Plot, get the associated actuatorId.

        // For now, we'll return a hardcoded policy for demonstration.
        var plot = plotRepository.findById(plotId);
        if (plot.isEmpty()) {
            // Return an "undefined" policy if plot doesn't exist
            return new IrrigationPolicy(plotId, -1, 0, null);
        }

        // Placeholder for actuatorId, should be part of the Plot aggregate
        UUID actuatorId = UUID.fromString("00000000-0000-0000-0000-000000000001"); 

        return new IrrigationPolicy(plotId, 35.0, 15, actuatorId); // Threshold: 35%, Duration: 15 mins
    }
}
