package com.agropapin.backend.organizationManagement.interfaces.rest;

import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import com.agropapin.backend.organizationManagement.domain.model.commands.AddNewAdministratorInCooperativeCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateCooperativeCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.DeleteCooperativeCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.UpdateCooperativeCommand;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByIdQuery;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeCommandService;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.*;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.CooperativeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/cooperative", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Cooperatives", description = "Cooperative Management Endpoints")
public class CooperativeController {
    private final CooperativeQueryService cooperativeQueryService;
    private final CooperativeCommandService cooperativeCommandService;
    private final IamContextFacade iamContextFacade;

    public CooperativeController(CooperativeQueryService cooperativeQueryService, CooperativeCommandService cooperativeCommandService, IamContextFacade iamContextFacade) {
        this.cooperativeQueryService = cooperativeQueryService;
        this.cooperativeCommandService = cooperativeCommandService;
        this.iamContextFacade = iamContextFacade;
    }

    @PostMapping(value = "/")
    public ResponseEntity<CooperativeResource> createCooperative(@Valid @RequestBody CreateCooperativeResource resource) {
        UUID currentUserId = iamContextFacade.getCurrentUserId();
        var createCooperativeCommand = new CreateCooperativeCommand(resource.cooperativeName(), currentUserId);
        var cooperative = cooperativeCommandService.handle(createCooperativeCommand);
        if (cooperative.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cooperativeResource = CooperativeResourceFromEntityAssembler.toResourceFromEntity(cooperative.get());
        return ResponseEntity.ok(cooperativeResource);
    }

    @GetMapping(value = "/{cooperativeId}")
    public ResponseEntity<CooperativeResource> getCooperativeById(@PathVariable UUID cooperativeId) {
        var getCooperativeByIdQuery = new GetCooperativeByIdQuery(cooperativeId);
        var cooperative = cooperativeQueryService.handle(getCooperativeByIdQuery);
        if (cooperative.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cooperativeResource = CooperativeResourceFromEntityAssembler.toResourceFromEntity(cooperative.get());
        return ResponseEntity.ok(cooperativeResource);
    }

    @PutMapping(value = "/{cooperativeId}")
    public ResponseEntity<CooperativeResource> updateCooperativeById(@PathVariable UUID cooperativeId, @RequestBody UpdateCooperativeResource resource) {
        var updateCooperativeCommand = new UpdateCooperativeCommand(cooperativeId, resource.name());
        var cooperative = cooperativeCommandService.handle(updateCooperativeCommand);
        if (cooperative.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cooperativeResource = CooperativeResourceFromEntityAssembler.toResourceFromEntity(cooperative.get());
        return ResponseEntity.ok(cooperativeResource);
    }

    @DeleteMapping(value = "/{cooperativeId}")
    public ResponseEntity<Void> deleteCooperativeById(@PathVariable UUID cooperativeId) {
        UUID currenUserId = iamContextFacade.getCurrentUserId();
        var deleteCooperativeCommand = new DeleteCooperativeCommand(cooperativeId, currenUserId);

        var cooperative = cooperativeCommandService.handle(deleteCooperativeCommand);
        if (cooperative.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{cooperativeId}/administrators")
    public ResponseEntity<CooperativeResource> addAdministratorToCooperative(
            @PathVariable UUID cooperativeId,
            @Valid @RequestBody AddAdministratorToCooperativeResource resource) {

        UUID currentUserId = iamContextFacade.getCurrentUserId();

        var addAdministratorCommand = new AddNewAdministratorInCooperativeCommand(
                cooperativeId,
                resource.administratorUserId(),
                currentUserId
        );

        var cooperative = cooperativeCommandService.handle(addAdministratorCommand);

        if (cooperative.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cooperativeResource = CooperativeResourceFromEntityAssembler.toResourceFromEntity(cooperative.get());
        return ResponseEntity.ok(cooperativeResource);
    }

    @PostMapping(value = "/{cooperativeId}/members")
    public ResponseEntity<CooperativeResource> addFarmerToCooperative(
            @PathVariable UUID cooperativeId,
            @Valid @RequestBody AddFarmerToCooperativeResource resource) {

        UUID currentUserId = iamContextFacade.getCurrentUserId();

        var addMemberCommand = new AddNewAdministratorInCooperativeCommand(
                cooperativeId,
                resource.farmerUserId(),
                currentUserId
        );

        var cooperative = cooperativeCommandService.handle(addMemberCommand);

        if (cooperative.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cooperativeResource = CooperativeResourceFromEntityAssembler.toResourceFromEntity(cooperative.get());
        return ResponseEntity.ok(cooperativeResource);
    }

}
