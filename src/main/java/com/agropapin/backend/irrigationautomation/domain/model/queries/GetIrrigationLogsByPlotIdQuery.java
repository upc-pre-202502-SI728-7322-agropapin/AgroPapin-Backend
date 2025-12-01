package com.agropapin.backend.irrigationautomation.domain.model.queries;

import java.util.UUID;

/**
 * Query to retrieve all irrigation log entries for a specific plot.
 */
public record GetIrrigationLogsByPlotIdQuery(UUID plotId) {
}
