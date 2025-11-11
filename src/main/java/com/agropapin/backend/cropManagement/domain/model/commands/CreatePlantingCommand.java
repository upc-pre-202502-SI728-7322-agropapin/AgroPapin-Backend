package com.agropapin.backend.cropManagement.domain.model.commands;

import java.util.Date;
import java.util.UUID;

public record CreatePlantingCommand(
        Date plantingDate,
        Date actualHarvestDate,
        UUID plotId,
        UUID cropTypeId
) {
}
