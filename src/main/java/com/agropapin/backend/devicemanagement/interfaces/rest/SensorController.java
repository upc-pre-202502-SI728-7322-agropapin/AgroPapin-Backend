package com.agropapin.backend.devicemanagement.interfaces.rest;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateSensorCommand;
import com.agropapin.backend.devicemanagement.domain.services.DeviceCommandService;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.CreateSensorResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.transform.CreateSensorCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {

    private final DeviceCommandService deviceCommandService;

    public SensorController(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    @PostMapping
    public ResponseEntity<UUID> createSensor(@RequestBody CreateSensorResource resource) {
        CreateSensorCommand command = CreateSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        UUID sensorId = deviceCommandService.handle(command);
        return new ResponseEntity<>(sensorId, HttpStatus.CREATED);
    }
}
