package com.agropapin.backend.irrigationautomation.interfaces.rest;

import com.agropapin.backend.irrigationautomation.domain.model.queries.GetIrrigationLogsByPlotIdQuery;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationLogQueryService;
import com.agropapin.backend.irrigationautomation.interfaces.rest.resources.IrrigationLogResource;
import com.agropapin.backend.irrigationautomation.interfaces.rest.transform.IrrigationLogResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/plots/{plotId}/irrigation-logs", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Irrigation Automation", description = "Endpoints for viewing irrigation history and decisions.")
public class IrrigationLogController {

    private final IrrigationLogQueryService irrigationLogQueryService;

    public IrrigationLogController(IrrigationLogQueryService irrigationLogQueryService) {
        this.irrigationLogQueryService = irrigationLogQueryService;
    }

    /**
     * Retrieves the historical log of irrigation decisions for a specific plot.
     * @param plotId The ID of the plot.
     * @return A list of irrigation log entries.
     */
    @GetMapping
    public ResponseEntity<List<IrrigationLogResource>> getIrrigationLogs(@PathVariable UUID plotId) {
        var query = new GetIrrigationLogsByPlotIdQuery(plotId);
        var logs = irrigationLogQueryService.handle(query);
        
        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var logResources = logs.stream()
                .map(IrrigationLogResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(logResources);
    }
}
