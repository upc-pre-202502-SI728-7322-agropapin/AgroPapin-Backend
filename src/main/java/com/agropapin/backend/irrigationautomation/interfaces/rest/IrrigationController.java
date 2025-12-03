package com.agropapin.backend.irrigationautomation.interfaces.rest;

import com.agropapin.backend.irrigationautomation.domain.model.commands.ActivateIrrigationCommand;
import com.agropapin.backend.irrigationautomation.domain.model.services.IrrigationCommandService;
import com.agropapin.backend.irrigationautomation.interfaces.rest.resources.ActivateIrrigationResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/irrigation", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Irrigation Automation", description = "Endpoints for manually commanding irrigation actions.")
public class IrrigationController {

    private final IrrigationCommandService irrigationCommandService;

    public IrrigationController(IrrigationCommandService irrigationCommandService) {
        this.irrigationCommandService = irrigationCommandService;
    }

    /**
     * Manually activates an irrigation actuator for a specified duration.
     * @param resource The activation details.
     * @return HTTP 202 Accepted.
     */
    @PostMapping("/activate")
    public ResponseEntity<Void> activateIrrigation(@RequestBody ActivateIrrigationResource resource) {
        var command = new ActivateIrrigationCommand(resource.actuatorId(), resource.minutes());
        irrigationCommandService.handle(command);
        return ResponseEntity.accepted().build();
    }
}
