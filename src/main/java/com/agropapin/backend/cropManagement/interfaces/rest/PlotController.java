package com.agropapin.backend.cropManagement.interfaces.rest;

import com.agropapin.backend.cropManagement.domain.model.commands.CreatePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeletePlotCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdatePlotStatusCommand;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllPlotByFieldIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetPlotByIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.PlotCommandService;
import com.agropapin.backend.cropManagement.domain.model.services.PlotQueryService;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.*;
import com.agropapin.backend.cropManagement.interfaces.rest.transform.PlotResourceFromEntityAssembler;
import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/fields/{fieldId}/plots", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plots", description = "Plot Management Endpoints")
public class PlotController {
    private final PlotQueryService plotQueryService;
    private final PlotCommandService plotCommandService;
    private final IamContextFacade iamContextFacade;

    public PlotController(PlotQueryService plotQueryService, PlotCommandService plotCommandService, IamContextFacade iamContextFacade) {
        this.plotQueryService = plotQueryService;
        this.plotCommandService = plotCommandService;
        this.iamContextFacade = iamContextFacade;
    }

    @GetMapping
    public ResponseEntity<List<PlotResource>> getPlots(@PathVariable UUID fieldId){
        var getPlotsCommand = new GetAllPlotByFieldIdQuery(fieldId);

        var plots = plotQueryService.handle(getPlotsCommand);

        if (plots.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var plotResources = PlotResourceFromEntityAssembler.toResourcesFromEntities(plots.get());

        return  ResponseEntity.status(HttpStatus.OK).body(plotResources);
    }

    @PostMapping
    public ResponseEntity<PlotResource> createPlot(@PathVariable UUID fieldId, @RequestBody CreatePlotResource createPlotResource) {

        var createPlotCommand = new CreatePlotCommand(
                createPlotResource.plotName(),
                createPlotResource.plotArea(),
                fieldId
        );

        var plot = plotCommandService.handle(createPlotCommand);

        if (plot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var plotResource = PlotResourceFromEntityAssembler.toResourceFromEntity(plot.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(plotResource);
    }

    @GetMapping("/{plotId}")
    public ResponseEntity<PlotResource> getPlot(@PathVariable UUID fieldId, @PathVariable UUID plotId) {
        var getPlotCommand = new GetPlotByIdQuery(fieldId, plotId);

        var plot = plotQueryService.handle(getPlotCommand);

        if (plot.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var plotResource = PlotResourceFromEntityAssembler.toResourceFromEntity(plot.get());

        return  ResponseEntity.status(HttpStatus.OK).body(plotResource);
    }

    @PutMapping("/{plotId}")
    public ResponseEntity<PlotResource> updatePlot(@PathVariable UUID fieldId, @PathVariable UUID plotId, @RequestBody UpdatePlotRecourse updatePlotResource){

        var updatePlotCommand = new UpdatePlotDataCommand(
                plotId,
                updatePlotResource.plotName(),
                updatePlotResource.plotArea(),
                fieldId
        );

        var plot = plotCommandService.handle(updatePlotCommand);

        if (plot.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var plotResource = PlotResourceFromEntityAssembler.toResourceFromEntity(plot.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(plotResource);
    }

    @DeleteMapping("/{plotId}")
    public ResponseEntity<Void> deletePlot(@PathVariable UUID fieldId, @PathVariable UUID plotId){

        var deletePlotCommand = new DeletePlotCommand(
                plotId,
                fieldId
        );

        var plot = plotCommandService.handle(deletePlotCommand);

        if (plot.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{plotId}/status")
    public ResponseEntity<PlotResource> updatePlotStatus(
            @PathVariable("fieldId") UUID fieldId,
            @PathVariable("plotId") UUID plotId,
            @Valid @RequestBody UpdatePlotStatusResource updatePlotStatusResource) {

        var updatePlotStatusCommand = new UpdatePlotStatusCommand(
                plotId,
                fieldId,
                updatePlotStatusResource.status()
        );

        var updatedPlot = plotCommandService.handle(updatePlotStatusCommand);

        return updatedPlot.map(plot -> ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(plot)))
                .orElse(ResponseEntity.notFound().build());
    }
}
