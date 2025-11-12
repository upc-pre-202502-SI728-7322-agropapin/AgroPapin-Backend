package com.agropapin.backend.irrigationautomation.interfaces.rest.transform;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import com.agropapin.backend.irrigationautomation.interfaces.rest.resources.IrrigationLogResource;

public class IrrigationLogResourceFromEntityAssembler {

    public static IrrigationLogResource toResourceFromEntity(IrrigationLog entity) {
        return new IrrigationLogResource(
                entity.getId(),
                entity.getPlotId(),
                entity.getDecisionTimestamp(),
                entity.getDecision(),
                entity.getReason(),
                entity.getHumidityReading(),
                entity.getHumidityThreshold()
        );
    }
}
