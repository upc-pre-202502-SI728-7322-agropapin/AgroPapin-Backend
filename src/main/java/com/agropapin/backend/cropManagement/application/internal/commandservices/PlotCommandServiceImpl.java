package com.agropapin.backend.cropManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.exceptions.PlotNotFoundException;
import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeletePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotStatusCommand;
import com.agropapin.backend.cropManagement.domain.model.services.PlotCommandService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlotCommandServiceImpl implements PlotCommandService {

    public final PlotRepository plotRepository;

    public PlotCommandServiceImpl(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    @Override
    public Optional<Plot> handle(CreatePlotCommand createPlotCommand) {

        var plot = new Plot(
                createPlotCommand.plotName(),
                createPlotCommand.area(),
                createPlotCommand.fieldId()
        );

        var savedPlot = this.plotRepository.save(plot);
        return Optional.of(savedPlot);
    }

    @Override
    public Optional<Plot> handle(UpdatePlotDataCommand updatePlotDataCommand) {
        var plot = plotRepository.findPlotByIdAndFieldId(updatePlotDataCommand.plotId(), updatePlotDataCommand.fieldId());

        if (plot.isEmpty()) {
            throw new IllegalArgumentException(
                    "No plot found with id: " + updatePlotDataCommand.plotId());
        }

        return plot.map(plotData -> {
            plotData.updateInfo(
                    updatePlotDataCommand.plotName(),
                    updatePlotDataCommand.area()
            );

            return plotRepository.save(plotData);
        });
    }

    @Override
    public Optional<Void> handle(DeletePlotCommand deletePlotCommand) {
        var plot = plotRepository.findPlotByIdAndFieldId(deletePlotCommand.plotId(), deletePlotCommand.fieldId());

        if(plot.isEmpty()) {
            throw new IllegalArgumentException(
                    "No plot found with id: " + deletePlotCommand.plotId());
        }

        plotRepository.deleteById(plot.get().getId());

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Plot> handle(UpdatePlotStatusCommand updatePlotStatusCommand) {
        var plot = plotRepository.findPlotByIdAndFieldId(updatePlotStatusCommand.plotId(), updatePlotStatusCommand.fieldId())
                .orElseThrow(() -> new PlotNotFoundException(updatePlotStatusCommand.plotId()));

        plot.updateStatus(updatePlotStatusCommand.plotStatus());

        var savedPlot = plotRepository.save(plot);

        return Optional.of(savedPlot);
    }
}
