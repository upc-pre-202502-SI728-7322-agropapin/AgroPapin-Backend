package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Plot;
import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotDataCommand;

import java.util.Optional;

public interface PlotCommandService {
    Optional<Plot> handle(CreatePlotCommand createPlotCommand);
    Optional<Plot> handle(UpdatePlotDataCommand updatePlotDataCommand);
}
