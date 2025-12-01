package com.agropapin.backend.cropManagement.interfaces.rest.transform;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Control;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.ControlResource;

public class ControlResourceFromEntityAssembler {

    public static ControlResource toResourceFromEntity(Control entity) {
        return new ControlResource(
                entity.getId(),
                entity.getDate(),
                entity.getStateLeaves(),
                entity.getStateStem(),
                entity.getSoilMoisture(),
                entity.getPlantingId(),
                entity.getPlot().getId()
        );
    }
}
