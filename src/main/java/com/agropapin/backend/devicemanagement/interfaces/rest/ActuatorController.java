package com.agropapin.backend.devicemanagement.interfaces.rest;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateActuatorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.UpdateActuatorStatusCommand;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetAllActuatorsByPlotIdQuery;
import com.agropapin.backend.devicemanagement.domain.services.DeviceCommandService;
import com.agropapin.backend.devicemanagement.domain.services.DeviceQueryService;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.ActuatorResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.CreateActuatorResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.UpdateDeviceStatusResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.transform.ActuatorResourceFromEntityAssembler;
import com.agropapin.backend.devicemanagement.interfaces.rest.transform.CreateActuatorCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/actuators", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Actuator Management", description = "Endpoints for managing actuators")
public class ActuatorController {

    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;

    public ActuatorController(DeviceCommandService deviceCommandService, DeviceQueryService deviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    @PostMapping
    public ResponseEntity<UUID> createActuator(@RequestBody CreateActuatorResource resource) {
        CreateActuatorCommand command = CreateActuatorCommandFromResourceAssembler.toCommandFromResource(resource);
        UUID actuatorId = deviceCommandService.handle(command);
        return new ResponseEntity<>(actuatorId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/plot/{plotId}")
    public ResponseEntity<List<ActuatorResource>> getActuatorsByPlotId(@PathVariable UUID plotId) {
        var query = new GetAllActuatorsByPlotIdQuery(plotId);
        var actuators = deviceQueryService.handle(query);

        if (actuators.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var actuatorListResources = ActuatorResourceFromEntityAssembler.toResourceFromEntities(actuators.get());

        return ResponseEntity.status(HttpStatus.OK).body(actuatorListResources);
    }

    @PatchMapping(value = "/plot/{plotId}/device/{actuatorId}/status")
    public ResponseEntity<ActuatorResource> updateActuatorStatus(
            @PathVariable UUID plotId,
            @PathVariable UUID actuatorId,
            @Valid @RequestBody UpdateDeviceStatusResource resource) {

        var updateActuatorStatusCommand = new UpdateActuatorStatusCommand(
                plotId,
                actuatorId,
                resource.status()
        );

        var updatedActuator = deviceCommandService.handle(updateActuatorStatusCommand);

        return updatedActuator.map(actuator -> ResponseEntity.ok(ActuatorResourceFromEntityAssembler.toResourceFromEntity(actuator)))
                .orElse(ResponseEntity.notFound().build());
    }
}
