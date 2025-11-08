package com.agropapin.backend.devicemanagement.interfaces.rest;

import com.agropapin.backend.devicemanagement.domain.model.commands.CreateActuatorCommand;
import com.agropapin.backend.devicemanagement.domain.services.DeviceCommandService;
import com.agropapin.backend.devicemanagement.interfaces.rest.resources.CreateActuatorResource;
import com.agropapin.backend.devicemanagement.interfaces.rest.transform.CreateActuatorCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actuators")
public class ActuatorController {

    private final DeviceCommandService deviceCommandService;

    public ActuatorController(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    @PostMapping
    public ResponseEntity<UUID> createActuator(@RequestBody CreateActuatorResource resource) {
        CreateActuatorCommand command = CreateActuatorCommandFromResourceAssembler.toCommandFromResource(resource);
        UUID actuatorId = deviceCommandService.handle(command);
        return new ResponseEntity<>(actuatorId, HttpStatus.CREATED);
    }
}
