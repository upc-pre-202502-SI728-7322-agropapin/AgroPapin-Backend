package com.agropapin.backend.cropManagement.interfaces.rest;

import com.agropapin.backend.cropManagement.domain.model.commands.CreateControlCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeleteControlCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateControlCommand;
import com.agropapin.backend.cropManagement.domain.model.queries.GetControlsByPlantingIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetControlsByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.ControlCommandService;
import com.agropapin.backend.cropManagement.domain.model.services.ControlQueryService;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.ControlResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.CreateControlResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.UpdateControlResource;
import com.agropapin.backend.cropManagement.interfaces.rest.transform.ControlResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/plots/{plotId}/controls", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Controls", description = "Crop Control and Monitoring Endpoints")
public class ControlController {

    private final ControlCommandService controlCommandService;
    private final ControlQueryService controlQueryService;

    public ControlController(ControlCommandService controlCommandService, ControlQueryService controlQueryService) {
        this.controlCommandService = controlCommandService;
        this.controlQueryService = controlQueryService;
    }

    @PostMapping
    public ResponseEntity<ControlResource> createControl(@PathVariable UUID plotId, @RequestBody CreateControlResource resource) {
        var command = new CreateControlCommand(
                resource.date(),
                resource.stateLeaves(),
                resource.stateStem(),
                resource.soilMoisture(),
                resource.plantingId(),
                plotId
        );
        var control = controlCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Error creating control"));

        var controlResource = ControlResourceFromEntityAssembler.toResourceFromEntity(control);
        return new ResponseEntity<>(controlResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ControlResource>> getControls(
            @PathVariable UUID plotId,
            @RequestParam(required = false) UUID plantingId) {

        List<ControlResource> controlResources;

        if (plantingId != null) {
            // Filter by plantingId
            var query = new GetControlsByPlantingIdQuery(plantingId);
            var controls = controlQueryService.handle(query);
            controlResources = controls.stream()
                    .map(ControlResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        } else {
            // Get all for the plotId
            var query = new GetControlsByPlotIdQuery(plotId);
            var controls = controlQueryService.handle(query);
            controlResources = controls.stream()
                    .map(ControlResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(controlResources);
    }

    @PutMapping("/{controlId}")
    public ResponseEntity<ControlResource> updateControl(@PathVariable UUID plotId, @PathVariable UUID controlId, @RequestBody UpdateControlResource resource) {
        var command = new UpdateControlCommand(
                controlId,
                resource.date(),
                resource.stateLeaves(),
                resource.stateStem(),
                resource.soilMoisture()
        );
        var control = controlCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Control not found or error updating"));

        var controlResource = ControlResourceFromEntityAssembler.toResourceFromEntity(control);
        return ResponseEntity.ok(controlResource);
    }

    @DeleteMapping("/{controlId}")
    public ResponseEntity<Void> deleteControl(@PathVariable UUID plotId, @PathVariable UUID controlId) {
        var command = new DeleteControlCommand(controlId);
        if (!controlCommandService.handle(command)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
