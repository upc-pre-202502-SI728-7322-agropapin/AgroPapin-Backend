package com.agropapin.backend.organizationManagement.interfaces.rest;

import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateAdministratorByUserIdCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateFarmerByUserIdCommand;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorCommandService;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.AdministratorResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.FarmerResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateAdministratorResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateFarmerResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.AdministratorResourceFromEntityAssembler;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.FarmerResourceFromEntityAssembler;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.UpdateAdministratorCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/administrator")
@Tag(name = "Administrators", description = "Administrator Management Endpoints")
public class AdministratorController {
    private final AdministratorQueryService administratorQueryService;
    private final AdministratorCommandService administratorCommandService;
    private final IamContextFacade iamContextFacade;

    public AdministratorController(AdministratorQueryService administratorQueryService, AdministratorCommandService administratorCommandService, IamContextFacade iamContextFacade) {
        this.administratorQueryService = administratorQueryService;
        this.administratorCommandService = administratorCommandService;
        this.iamContextFacade = iamContextFacade;
    }

    @GetMapping(value = "/my")
    public ResponseEntity<AdministratorResource> getMyAdministratorProfile() {
        var userId = iamContextFacade.getCurrentUserId();

        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);

        var administrator = administratorQueryService.handle(getAdministratorByUserIdQuery);

        if (administrator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var administratorResource = AdministratorResourceFromEntityAssembler.toResourceFromEntity(administrator.get());
        return ResponseEntity.ok(administratorResource);
    }

    @PutMapping(value = "/my")
    public ResponseEntity<AdministratorResource> updateMyFarmerProfile(@RequestBody UpdateAdministratorByUserIdCommand updateAdministratorByUserIdCommand) {
        var userId = iamContextFacade.getCurrentUserId();

        var updateAdministratorCommand = new UpdateAdministratorByUserIdCommand(
                userId,
                updateAdministratorByUserIdCommand.firstName(),
                updateAdministratorByUserIdCommand.lastName(),
                updateAdministratorByUserIdCommand.country(),
                updateAdministratorByUserIdCommand.phoneNumber()
        );

        var administrator = administratorCommandService.handle(updateAdministratorCommand);
        if (administrator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var administratorResource = AdministratorResourceFromEntityAssembler.toResourceFromEntity(administrator.get());
        return ResponseEntity.ok(administratorResource);
    }

    @GetMapping(value = "/{administratorId}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<AdministratorResource> getAdministratorById(@PathVariable UUID administratorId) {
        var getAdministratorByIdQuery = new GetAdministratorByIdQuery(administratorId);
        var administrator = administratorQueryService.handle(getAdministratorByIdQuery);
        if (administrator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var administratorResource = AdministratorResourceFromEntityAssembler.toResourceFromEntity(administrator.get());
        return ResponseEntity.ok(administratorResource);
    }

    @GetMapping(value = "/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<AdministratorResource> getAdministratorByUserId(@PathVariable String userId) {
        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);
        var administrator = administratorQueryService.handle(getAdministratorByUserIdQuery);
        if (administrator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var administratorResource = AdministratorResourceFromEntityAssembler.toResourceFromEntity(administrator.get());
        return ResponseEntity.ok(administratorResource);
    }

//    @PutMapping(value = "/{userId}")
//    public ResponseEntity<AdministratorResource> updateAdministratorByUserId(@PathVariable String userId, @RequestBody UpdateAdministratorResource resource) {
//        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);
//        var administrator = administratorQueryService.handle(getAdministratorByUserIdQuery);
//        if (administrator.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        var updateAdministratorCommand = UpdateAdministratorCommandFromResourceAssembler.toCommandFromResource(administrator.get().getId(), resource);
//        var updatedAdministrator = administratorCommandService.handle(updateAdministratorCommand);
//        if (updatedAdministrator.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        var administratorResourceUpdated = AdministratorResourceFromEntityAssembler.toResourceFromEntity(updatedAdministrator.get());
//        return ResponseEntity.ok(administratorResourceUpdated);
//    }
}
