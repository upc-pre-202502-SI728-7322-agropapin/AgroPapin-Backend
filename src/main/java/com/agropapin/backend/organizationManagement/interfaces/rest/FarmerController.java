package com.agropapin.backend.organizationManagement.interfaces.rest;


import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerByUserIdCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerInfoCommand;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.FarmerCommandService;
import com.agropapin.backend.organizationManagement.domain.services.FarmerQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.FarmerResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateFarmerResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.FarmerResourceFromEntityAssembler;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.UpdateFarmerCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farmer")
@Tag(name = "Farmers", description = "Farmer Management Endpoints")
public class FarmerController {

    private final FarmerQueryService farmerQueryService;
    private final FarmerCommandService farmerCommandService;
    private final IamContextFacade iamContextFacade;

    public FarmerController(FarmerQueryService farmerQueryService, FarmerCommandService farmerCommandService, IamContextFacade iamContextFacade) {
        this.farmerQueryService = farmerQueryService;
        this.farmerCommandService = farmerCommandService;
        this.iamContextFacade = iamContextFacade;
    }

    @GetMapping(value = "/me")
    public ResponseEntity<FarmerResource> getMyFarmerProfile() {
        var userId = iamContextFacade.getCurrentUserId();

        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);

        var farmer = farmerQueryService.handle(getFarmerByUserIdQuery);

        if (farmer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var farmerResource = FarmerResourceFromEntityAssembler.toResourceFromEntity(farmer.get());
        return ResponseEntity.ok(farmerResource);
    }

    @PutMapping(value = "/me")
    public ResponseEntity<FarmerResource> updateMyFarmerProfile(@RequestBody UpdateFarmerResource updateFarmerResource) {
        var userId = iamContextFacade.getCurrentUserId();

        var updateFarmerCommand = new UpdateFarmerByUserIdCommand(
                userId,
                updateFarmerResource.firstName(),
                updateFarmerResource.lastName(),
                updateFarmerResource.country(),
                updateFarmerResource.phone()
        );

        var farmer = farmerCommandService.handle(updateFarmerCommand);
        if (farmer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var farmerResource = FarmerResourceFromEntityAssembler.toResourceFromEntity(farmer.get());
        return ResponseEntity.ok(farmerResource);
    }

    @GetMapping(value = "/{farmerId}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<FarmerResource> getFarmerById(@PathVariable UUID farmerId) {
        var getFarmerByIdQuery = new GetFarmerByIdQuery(farmerId);
        var farmer = farmerQueryService.handle(getFarmerByIdQuery);
        if (farmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var farmerResource = FarmerResourceFromEntityAssembler.toResourceFromEntity(farmer.get());
        return ResponseEntity.ok(farmerResource);
    }

    @GetMapping(value = "/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<FarmerResource> getFarmerByUserId(@PathVariable String userId) {
        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);
        var farmer = farmerQueryService.handle(getFarmerByUserIdQuery);
        if (farmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var farmerResource = FarmerResourceFromEntityAssembler.toResourceFromEntity(farmer.get());
        return ResponseEntity.ok(farmerResource);
    }

    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<FarmerResource> updateFarmerByUserId(@PathVariable String userId, @RequestBody UpdateFarmerResource resource) {
        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);
        var farmer = farmerQueryService.handle(getFarmerByUserIdQuery);
        if (farmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updateFarmerCommand = UpdateFarmerCommandFromResourceAssembler.toCommandFromResource(farmer.get().getId(), resource);
        var updatedFarmer = farmerCommandService.handle(updateFarmerCommand);
        if (updatedFarmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var farmerResourceUpdated = FarmerResourceFromEntityAssembler.toResourceFromEntity(updatedFarmer.get());
        return ResponseEntity.ok(farmerResourceUpdated);
    }

    @GetMapping(value = "/" )
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Farmer Service is up and running!");
    }
}
