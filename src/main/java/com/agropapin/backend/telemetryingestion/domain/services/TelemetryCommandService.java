package com.agropapin.backend.telemetryingestion.domain.services;

import com.agropapin.backend.telemetryingestion.domain.model.commands.IngestTelemetryCommand;

public interface TelemetryCommandService {
    void handle(IngestTelemetryCommand command);
}
