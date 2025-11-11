package com.agropapin.backend.telemetryingestion.application.internal.commandservices;

import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryWriter;
import com.agropapin.backend.telemetryingestion.domain.model.commands.IngestTelemetryCommand;
import com.agropapin.backend.telemetryingestion.domain.services.TelemetryCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelemetryCommandServiceImpl implements TelemetryCommandService {

    private static final Logger log = LoggerFactory.getLogger(TelemetryCommandServiceImpl.class);
    private final TelemetryWriter telemetryWriter;

    public TelemetryCommandServiceImpl(TelemetryWriter telemetryWriter) {
        this.telemetryWriter = telemetryWriter;
    }

    @Override
    public void handle(IngestTelemetryCommand command) {
        if (command.readings() == null || command.readings().isEmpty()) {
            log.warn("IngestTelemetryCommand received with no readings to process.");
            return;
        }
        log.info("Handling IngestTelemetryCommand with {} readings. Passing to repository.", command.readings().size());
        this.telemetryWriter.writeBatch(command.readings());
    }
}
