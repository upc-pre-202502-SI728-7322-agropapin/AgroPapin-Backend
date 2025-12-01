package com.agropapin.backend.telemetryingestion.domain.model.events;

import java.time.Instant;
import java.util.UUID;

/**
 * An Application Event published when a new telemetry reading has been successfully ingested.
 * This event decouples the ingestion process from any automated decision-making processes.
 *
 * @param plotId The ID of the plot the reading belongs to.
 * @param humidity The specific humidity value from the reading, as it's critical for irrigation decisions.
 * @param timestamp The time the reading was taken.
 */
public record TelemetryReadingReceivedEvent(
        UUID plotId,
        double humidity,
        Instant timestamp
) {
}
