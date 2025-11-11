package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.util.Date;
import java.util.UUID;

public record UpdatePlantingResource(Date plantingDate, Date harvestDate, UUID cropId) {
}
