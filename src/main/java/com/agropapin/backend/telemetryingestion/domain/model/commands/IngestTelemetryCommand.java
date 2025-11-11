package com.agropapin.backend.telemetryingestion.domain.model.commands;

import com.agropapin.backend.telemetryingestion.domain.model.valueobjects.PlotReading;

import java.util.List;

/**
 * Command to ingest a batch of telemetry data.
 * It contains the list of readings to be persisted.
 */
public record IngestTelemetryCommand(List<PlotReading> readings) {
    public IngestTelemetryCommand {
        if (readings == null || readings.isEmpty()) {
            throw new IllegalArgumentException("Readings list cannot be null or empty.");
        }
    }
}
