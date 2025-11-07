package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeletePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingStatusCommand;

import java.util.Optional;

public interface PlantingCommandService {
    Optional<Planting> handle(CreatePlantingCommand command);
    Optional<Planting> handle(UpdatePlantingDataCommand command);
    void handle(DeletePlantingCommand command);
    Optional<Planting> handle(UpdatePlantingStatusCommand command);
}
