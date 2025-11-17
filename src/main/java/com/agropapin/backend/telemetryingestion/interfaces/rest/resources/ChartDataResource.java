package com.agropapin.backend.telemetryingestion.interfaces.rest.resources;

import java.time.Instant;

public record ChartDataResource(
        Instant time,
        Float temperature,
        Float humidity,
        Float soilMoisture
) {}
