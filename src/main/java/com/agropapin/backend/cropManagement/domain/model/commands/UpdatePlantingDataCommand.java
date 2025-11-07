package com.agropapin.backend.cropManagement.domain.model.commands;

import java.util.Date;
import java.util.UUID;

public record UpdatePlantingDataCommand(UUID plantingId, Date plantingDate, Date harvestDate, UUID cropId, UUID plotId) {
}
