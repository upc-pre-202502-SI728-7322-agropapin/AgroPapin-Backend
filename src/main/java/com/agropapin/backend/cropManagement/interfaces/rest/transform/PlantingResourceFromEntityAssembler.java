package com.agropapin.backend.cropManagement.interfaces.rest.transform;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.PlantingResource;

import java.util.List;
import java.util.stream.Collectors;

public class PlantingResourceFromEntityAssembler {
    public static PlantingResource toResourceFromEntity(Planting entity) {
        return new PlantingResource(entity.getId(), entity.getPlantingDate(), entity.getActualHarvestDate(), entity.getStatus(), entity.getPlotId());
    }

    public static List<PlantingResource> toResourcesFromEntities(List<Planting> entities) {
        return entities.stream().map(PlantingResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
    }
}
