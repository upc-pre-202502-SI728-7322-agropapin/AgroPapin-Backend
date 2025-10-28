package com.agropapin.backend.organizationManagement.interfaces.rest;

import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorCommandService;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.AdministratorResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateAdministratorResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.AdministratorResourceFromEntityAssembler;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.UpdateAdministratorCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrators")
@Tag(name = "Administrators", description = "Administrator Management Endpoints")
public class AdministratorController {
    private final AdministratorQueryService administratorQueryService;
    private final AdministratorCommandService administratorCommandService;

    public AdministratorController(AdministratorQueryService administratorQueryService, AdministratorCommandService administratorCommandService) {
        this.administratorQueryService = administratorQueryService;
        this.administratorCommandService = administratorCommandService;
    }

    @GetMapping(value = "/{administratorId}")
    public ResponseEntity<AdministratorResource> getAdministratorById(@PathVariable Long administratorId) {
        var getAdministratorByIdQuery = new GetAdministratorByIdQuery(administratorId);
        var administrator = administratorQueryService.handle(getAdministratorByIdQuery);
        if (administrator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var administratorResource = AdministratorResourceFromEntityAssembler.toResourceFromEntity(administrator.get());
        return ResponseEntity.ok(administratorResource);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<AdministratorResource> getAdministratorByUserId(@PathVariable Long userId) {
        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);
        var administrator = administratorQueryService.handle(getAdministratorByUserIdQuery);
        if (administrator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var administratorResource = AdministratorResourceFromEntityAssembler.toResourceFromEntity(administrator.get());
        return ResponseEntity.ok(administratorResource);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<AdministratorResource> updateAdministratorByUserId(@PathVariable Long userId, @RequestBody UpdateAdministratorResource resource) {
        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);
        var administrator = administratorQueryService.handle(getAdministratorByUserIdQuery);
        if (administrator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updateAdministratorCommand = UpdateAdministratorCommandFromResourceAssembler.toCommandFromResource(administrator.get().getUserId(), resource);
        var updatedAdministrator = administratorCommandService.handle(updateAdministratorCommand);
        if (updatedAdministrator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var administratorResourceUpdated = AdministratorResourceFromEntityAssembler.toResourceFromEntity(updatedAdministrator.get());
        return ResponseEntity.ok(administratorResourceUpdated);
    }
}
