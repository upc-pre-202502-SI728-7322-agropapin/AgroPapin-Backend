package com.agropapin.backend.organizationManagement.interfaces.rest;


import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.FarmerCommandService;
import com.agropapin.backend.organizationManagement.domain.services.FarmerQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.FarmerResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateFarmerResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.FarmerResourceFromEntityAssembler;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.UpdateFarmerCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farmers")
@Tag(name = "Farmers", description = "Farmer Management Endpoints")
public class FarmerController {

    private final FarmerQueryService farmerQueryService;
    private final FarmerCommandService farmerCommandService;

    public FarmerController(FarmerQueryService farmerQueryService, FarmerCommandService farmerCommandService) {
        this.farmerQueryService = farmerQueryService;
        this.farmerCommandService = farmerCommandService;
    }

    @GetMapping(value = "/{farmerId}")
    public ResponseEntity<FarmerResource> getFarmerById(@PathVariable Long farmerId) {
        var getFarmerByIdQuery = new GetFarmerByIdQuery(farmerId);
        var farmer = farmerQueryService.handle(getFarmerByIdQuery);
        if (farmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var farmerResource = FarmerResourceFromEntityAssembler.toResourceFromEntity(farmer.get());
        return ResponseEntity.ok(farmerResource);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<FarmerResource> getFarmerByUserId(@PathVariable Long userId) {
        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);
        var farmer = farmerQueryService.handle(getFarmerByUserIdQuery);
        if (farmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var farmerResource = FarmerResourceFromEntityAssembler.toResourceFromEntity(farmer.get());
        return ResponseEntity.ok(farmerResource);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<FarmerResource> updateFarmerByUserId(@PathVariable Long userId, @RequestBody UpdateFarmerResource resource) {
        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);
        var farmer = farmerQueryService.handle(getFarmerByUserIdQuery);
        if (farmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updateFarmerCommand = UpdateFarmerCommandFromResourceAssembler.toCommandFromResource(farmer.get().getUserId(), resource);
        var updatedFarmer = farmerCommandService.handle(updateFarmerCommand);
        if (updatedFarmer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var farmerResourceUpdated = FarmerResourceFromEntityAssembler.toResourceFromEntity(updatedFarmer.get());
        return ResponseEntity.ok(farmerResourceUpdated);
    }

}
