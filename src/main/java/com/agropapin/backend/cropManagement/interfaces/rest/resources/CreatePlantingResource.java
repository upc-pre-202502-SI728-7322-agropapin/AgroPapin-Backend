package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.util.Date;
import java.util.UUID;

public record CreatePlantingResource(
        Date plantingDate,
        Date actualHarvestDate,
        UUID cropTypeId
) {
}
