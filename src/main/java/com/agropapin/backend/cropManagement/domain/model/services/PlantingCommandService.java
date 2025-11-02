package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingDataCommand;

import java.util.Optional;

public interface PlantingCommandService {
    Optional<Planting> handle(CreatePlantingCommand createPlantingCommand);
    Optional<Planting> handle(UpdatePlantingDataCommand updatePlantingDataCommand);
}
