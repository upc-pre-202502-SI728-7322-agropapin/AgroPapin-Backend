package com.agropapin.backend.cropManagement.interfaces.rest;

import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeletePlantingCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlantingStatusCommand;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlantingByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlantingByIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.PlantingCommandService;
import com.agropapin.backend.cropManagement.domain.model.services.PlantingQueryService;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.*;
import com.agropapin.backend.cropManagement.interfaces.rest.transform.PlantingResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/fields/{fieldId}/plots/{plotId}/plantings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plantings", description = "Planting Cycle Management Endpoints")
public class PlantingController {
    private final PlantingQueryService plantingQueryService;
    private final PlantingCommandService plantingCommandService;

    public PlantingController(PlantingQueryService plantingQueryService, PlantingCommandService plantingCommandService) {
        this.plantingQueryService = plantingQueryService;
        this.plantingCommandService = plantingCommandService;
    }

    @GetMapping
    public ResponseEntity<List<PlantingResource>> getPlantings(@PathVariable UUID fieldId, @PathVariable UUID plotId){
        var getPlantingsQuery = new GetAllPlantingByPlotIdQuery(plotId);
        var plantings = plantingQueryService.handle(getPlantingsQuery);
        if (plantings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var plantingResources = PlantingResourceFromEntityAssembler.toResourcesFromEntities(plantings);
        return ResponseEntity.ok(plantingResources);
    }

    @PostMapping
    public ResponseEntity<PlantingResource> createPlanting(@PathVariable UUID fieldId, @PathVariable UUID plotId, @RequestBody CreatePlantingResource createPlantingResource) {
        var createPlantingCommand = new CreatePlantingCommand(
                createPlantingResource.plantingDate(),
                createPlantingResource.actualHarvestDate(),
                plotId,
                createPlantingResource.cropTypeId()
        );
        var planting = plantingCommandService.handle(createPlantingCommand);
        if (planting.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var plantingResource = PlantingResourceFromEntityAssembler.toResourceFromEntity(planting.get());
        return new ResponseEntity<>(plantingResource, HttpStatus.CREATED);
    }

    @GetMapping("/{plantingId}")
    public ResponseEntity<PlantingResource> getPlanting(@PathVariable UUID fieldId, @PathVariable UUID plotId, @PathVariable UUID plantingId) {
        var getPlantingByIdQuery = new GetPlantingByIdQuery(plotId, plantingId);
        var planting = plantingQueryService.handle(getPlantingByIdQuery);
        if (planting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var plantingResource = PlantingResourceFromEntityAssembler.toResourceFromEntity(planting.get());
        return ResponseEntity.ok(plantingResource);
    }

    @PutMapping("/{plantingId}")
    public ResponseEntity<PlantingResource> updatePlanting(@PathVariable UUID fieldId, @PathVariable UUID plotId, @PathVariable UUID plantingId, @RequestBody UpdatePlantingResource updatePlantingResource){
        var updatePlantingDataCommand = new UpdatePlantingDataCommand(
                plantingId,
                updatePlantingResource.plantingDate(),
                updatePlantingResource.harvestDate(),
                updatePlantingResource.cropId(),
                plotId
        );
        var planting = plantingCommandService.handle(updatePlantingDataCommand);
        if (planting.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var plantingResource = PlantingResourceFromEntityAssembler.toResourceFromEntity(planting.get());
        return ResponseEntity.ok(plantingResource);
    }

    @DeleteMapping("/{plantingId}")
    public ResponseEntity<Void> deletePlanting(@PathVariable UUID fieldId, @PathVariable UUID plotId, @PathVariable UUID plantingId){
        var deletePlantingCommand = new DeletePlantingCommand(plantingId, plotId);
        plantingCommandService.handle(deletePlantingCommand);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{plantingId}/status")
    public ResponseEntity<PlantingResource> updatePlantingStatus(
            @PathVariable UUID fieldId,
            @PathVariable UUID plotId,
            @PathVariable UUID plantingId,
            @Valid @RequestBody UpdatePlantingStatusResource updatePlantingStatusResource) {
        var updatePlantingStatusCommand = new UpdatePlantingStatusCommand(
                plantingId,
                plotId,
                updatePlantingStatusResource.status()
        );
        var updatedPlanting = plantingCommandService.handle(updatePlantingStatusCommand);
        return updatedPlanting.map(planting -> ResponseEntity.ok(PlantingResourceFromEntityAssembler.toResourceFromEntity(planting)))
                .orElse(ResponseEntity.notFound().build());
    }
}
