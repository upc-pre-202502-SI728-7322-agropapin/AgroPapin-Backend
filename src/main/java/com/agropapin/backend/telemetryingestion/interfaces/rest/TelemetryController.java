package com.agropapin.backend.telemetryingestion.interfaces.rest;

import com.agropapin.backend.telemetryingestion.application.internal.queryservices.TelemetryQueryServiceImpl;
import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryWriter;
import com.agropapin.backend.telemetryingestion.domain.model.commands.IngestTelemetryCommand;
import com.agropapin.backend.telemetryingestion.domain.services.TelemetryCommandService;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.AvgReadingResource;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.ChartDataResource;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.PlotReadingResource;
import com.agropapin.backend.telemetryingestion.interfaces.rest.transform.PlotReadingAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/telemetry", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Telemetry Ingestion", description = "Endpoints for high-throughput telemetry data ingestion.")
public class TelemetryController {

    private static final Logger log = LoggerFactory.getLogger(TelemetryController.class);
    private final TelemetryCommandService telemetryCommandService;
    private final TelemetryQueryServiceImpl telemetryQueryService;

    public TelemetryController(TelemetryCommandService telemetryCommandService, TelemetryQueryServiceImpl telemetryQueryService) {
        this.telemetryCommandService = telemetryCommandService;
        this.telemetryQueryService = telemetryQueryService;
    }

    /**
     * Ingests a batch of telemetry readings from Fog layer.
     * This endpoint is designed for high-throughput and should respond as quickly as possible.
     *
     * @param readings A list of plot readings.
     * @return HTTP 202 Accepted if the data is queued for processing.
     */
    @PostMapping("/ingest-batch")
    public ResponseEntity<Void> ingestTelemetryBatch(@RequestBody List<PlotReadingResource> readings) {
        if (readings == null || readings.isEmpty()) {
            log.warn("Received empty or null telemetry batch.");
            return ResponseEntity.badRequest().build();
        }

        log.info("Received telemetry batch with {} readings.", readings.size());
        log.debug("Telemetry data: {}", readings); // Use DEBUG level for verbose data

        var plotReadings = PlotReadingAssembler.toValueObjectsFromResources(readings);
        var command = new IngestTelemetryCommand(plotReadings);
        telemetryCommandService.handle(command);

        log.info("Telemetry batch for {} readings has been accepted for processing.", readings.size());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/latest-metrics/plot/{plotId}")
    public ResponseEntity<List<AvgReadingResource>> getLatestMetrics(
            @PathVariable String plotId
    ) {
        // Llama al servicio para obtener la última agregación de datos
        List<AvgReadingResource> metrics = telemetryQueryService.getLatestAggregatedMetrics(plotId);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/historical/plot/{plotId}/days/{days}")
    public ResponseEntity<List<ChartDataResource>> getHistoricalMetrics(
            @PathVariable String plotId,
            @PathVariable int days
    ){
        List<ChartDataResource> metrics = telemetryQueryService.getHistoricalMetrics(plotId, days);
        return ResponseEntity.ok(metrics);
    }
}
