package com.agropapin.backend.cropManagement.domain.exceptions;

import java.util.UUID;

public class PlotNotFoundException extends RuntimeException {
    private final UUID plotId;

    public PlotNotFoundException(UUID plotId) {
        super(String.format("Plot with id '%s' was not found", plotId));
        this.plotId = plotId;
    }

    public PlotNotFoundException(UUID plotId, Throwable cause) {
        super(String.format("Plot with id '%s' was not found", plotId), cause);
        this.plotId = plotId;
    }

    public UUID getPlotId() {
        return plotId;
    }
}
