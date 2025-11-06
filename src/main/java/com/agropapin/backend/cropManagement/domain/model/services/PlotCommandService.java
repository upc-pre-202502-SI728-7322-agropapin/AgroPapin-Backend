package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeletePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotStatusCommand;

import java.util.Optional;

public interface PlotCommandService {
    Optional<Plot> handle(CreatePlotCommand createPlotCommand);
    Optional<Plot> handle(UpdatePlotDataCommand updatePlotDataCommand);
    Optional<Void> handle(DeletePlotCommand deletePlotCommand);
    Optional<Plot> handle(UpdatePlotStatusCommand updatePlotStatusCommand);
}
