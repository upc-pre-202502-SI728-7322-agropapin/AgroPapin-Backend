package com.agropapin.backend.cropManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeletePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingStatusCommand;
import com.agropapin.backend.cropManagement.domain.model.services.PlantingCommandService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.CropTypeRepository;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlotRepository;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlantingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlantingCommandServiceImpl implements PlantingCommandService {
    private final PlantingRepository plantingRepository;
    private final PlotRepository plotRepository;
    private final CropTypeRepository cropTypeRepository;

    public PlantingCommandServiceImpl(PlantingRepository plantingRepository, PlotRepository plotRepository, CropTypeRepository cropTypeRepository) {
        this.plantingRepository = plantingRepository;
        this.plotRepository = plotRepository;
        this.cropTypeRepository = cropTypeRepository;
    }

    @Override
    public Optional<Planting> handle(CreatePlantingCommand command) {
        var plot = plotRepository.findById(command.plotId());
        if (plot.isEmpty()) {
            throw new IllegalArgumentException(
                    "No plot found with id: " + command.plotId()
            );
        }
        var cropType = this.cropTypeRepository.findById(command.cropTypeId());

        if (cropType.isEmpty()) {
            throw new IllegalArgumentException(
                    "No crop type found with id: " + command.cropTypeId()
            );
        }

        var planting = new Planting(
                command.plantingDate(),
                command.plotId(),
                cropType.get()
        );
        plantingRepository.save(planting);
        return Optional.of(planting);
    }

    @Override
    public Optional<Planting> handle(UpdatePlantingDataCommand command) {
        var planting = plantingRepository.findById(command.plantingId());
        if (planting.isEmpty()) {
            return Optional.empty();
        }
        planting.get().update(command.plantingDate(), command.harvestDate(), command.cropId());
        plantingRepository.save(planting.get());
        return planting;
    }

    @Override
    public void handle(DeletePlantingCommand command) {
        plantingRepository.deleteById(command.plantingId());
    }

    @Override
    @Transactional
    public Optional<Planting> handle(UpdatePlantingStatusCommand command) {
        var planting = plantingRepository.findById(command.plantingId());

        if (planting.isEmpty()) {
            return Optional.empty();
        }

        planting.get().updateStatus(command.status());
        plantingRepository.save(planting.get());
        return planting;
    }
}
