package com.agropapin.backend.cropManagement.domain.model.commands;

import java.util.UUID;

public record DeletePlantingCommand(UUID plantingId, UUID plotId) {
}
