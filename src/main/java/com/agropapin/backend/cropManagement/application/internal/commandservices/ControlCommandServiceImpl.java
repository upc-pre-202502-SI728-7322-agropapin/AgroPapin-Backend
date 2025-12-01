package com.agropapin.backend.cropManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Control;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateControlCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeleteControlCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateControlCommand;
import com.agropapin.backend.cropManagement.domain.model.services.ControlCommandService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.ControlRepository;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlotRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ControlCommandServiceImpl implements ControlCommandService {

    private final ControlRepository controlRepository;
    private final PlotRepository plotRepository;

    public ControlCommandServiceImpl(ControlRepository controlRepository, PlotRepository plotRepository) {
        this.controlRepository = controlRepository;
        this.plotRepository = plotRepository;
    }

    @Override
    public Optional<Control> handle(CreateControlCommand command) {
        var plot = plotRepository.findById(command.plotId())
                .orElseThrow(() -> new IllegalArgumentException("Plot not found with ID: " + command.plotId()));

        var control = new Control(
                command.date(),
                command.stateLeaves(),
                command.stateStem(),
                command.soilMoisture(),
                command.plantingId(),
                plot
        );
        controlRepository.save(control);
        return Optional.of(control);
    }

    @Override
    public Optional<Control> handle(UpdateControlCommand command) {
        return controlRepository.findById(command.controlId()).map(control -> {
            control.update(
                    command.date(),
                    command.stateLeaves(),
                    command.stateStem(),
                    command.soilMoisture()
            );
            controlRepository.save(control);
            return control;
        });
    }

    @Override
    public boolean handle(DeleteControlCommand command) {
        return controlRepository.findById(command.controlId()).map(control -> {
            controlRepository.delete(control);
            return true;
        }).orElse(false);
    }
}
