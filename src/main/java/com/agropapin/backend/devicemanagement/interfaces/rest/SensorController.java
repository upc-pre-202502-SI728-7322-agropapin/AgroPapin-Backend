package com.agropapin.backend.devicemanagement.interfaces.rest;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateSensorCommand;
import com.agropapin.backend.devicemanagement.domain.model.commands.UpdateSensorStatusCommand;
import com.agropapin.backend.devicemanagement.domain.model.queries.GetAllSensorsByPlotIdQuery;
import com.agropapin.backend.devicemanagement.domain.services.DeviceCommandService;
import com.agropapin.backend.devicemanagement.domain.services.DeviceQueryService;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.CreateSensorResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.SensorResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.UpdateDeviceStatusResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.transform.CreateSensorCommandFromResourceAssembler;
import com.agropapin.backend.devicemanagement.interfaces.rest.transform.SensorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/sensors", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Sensor Management", description = "Endpoints for managing sensors")
public class SensorController {

    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;

    public SensorController(DeviceCommandService deviceCommandService, DeviceQueryService deviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    @PostMapping
    public ResponseEntity<UUID> createSensor(@RequestBody CreateSensorResource resource) {
        CreateSensorCommand command = CreateSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        UUID sensorId = deviceCommandService.handle(command);
        return new ResponseEntity<>(sensorId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/plot/{plotId}")
    public ResponseEntity<List<SensorResource>> getSensorsByPlotId(@PathVariable UUID plotId) {
        var query = new GetAllSensorsByPlotIdQuery(plotId);
        var sensors = deviceQueryService.handle(query);

        if (sensors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var sensorListResources = SensorResourceFromEntityAssembler.toResourceFromEntities(sensors.get());

        return ResponseEntity.status(HttpStatus.OK).body(sensorListResources);
    }

    @PatchMapping(value = "/plot/{plotId}/device/{sensorId}/status")
    public ResponseEntity<SensorResource> updateActuatorStatus(
            @PathVariable UUID plotId,
            @PathVariable UUID sensorId,
            @Valid @RequestBody UpdateDeviceStatusResource resource) {

        var updateActuatorStatusCommand = new UpdateSensorStatusCommand(
                plotId,
                sensorId,
                resource.status()
        );

        var updatedActuator = deviceCommandService.handle(updateActuatorStatusCommand);

        return updatedActuator.map(actuator -> ResponseEntity.ok(SensorResourceFromEntityAssembler.toResourceFromEntity(actuator)))
                .orElse(ResponseEntity.notFound().build());
    }
}
