package com.agropapin.backend.cropManagement.interfaces.rest.transform;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.FieldResource;

public class FieldResourceFromEntityAssembler {
    public static FieldResource toResourceFromEntity(Field entity) {
        return new FieldResource(
                entity.getId(),
                entity.getFieldName(),
                entity.getLocation(),
                entity.getTotalArea(),
                entity.getStatus()
        );
    }
}
