package com.agropapin.backend.cropManagement.interfaces.rest;

import com.agropapin.backend.cropManagement.domain.model.commands.CreateFieldCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldDataCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateFieldStatusCommand;
import com.agropapin.backend.cropManagement.domain.model.queries.GetFieldByFarmerIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.FieldCommandService;
import com.agropapin.backend.cropManagement.domain.model.services.FieldQueryService;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.*;
import com.agropapin.backend.cropManagement.interfaces.rest.transform.FieldResourceFromEntityAssembler;
import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import com.agropapin.backend.organizationManagement.interfaces.acl.OrganizationManagementFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/field", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fields", description = "Field Management Endpoints")
public class FieldController {
    private final FieldQueryService fieldQueryService;
    private final FieldCommandService fieldCommandService;
    private final IamContextFacade iamContextFacade;
    private final OrganizationManagementFacade organizationManagementFacade;

    public FieldController(FieldQueryService fieldQueryService, FieldCommandService fieldCommandService, IamContextFacade iamContextFacade, OrganizationManagementFacade organizationManagementFacade) {
        this.fieldQueryService = fieldQueryService;
        this.fieldCommandService = fieldCommandService;
        this.iamContextFacade = iamContextFacade;
        this.organizationManagementFacade = organizationManagementFacade;
    }

    @PostMapping(value = "/me")
    public ResponseEntity<FieldResource> createField(@Valid @RequestBody CreateFieldResource createFieldResource) {
        var userId = iamContextFacade.getCurrentUserId();

        var createFieldCommand = new CreateFieldCommand(
                userId,
                createFieldResource.fieldName(),
                createFieldResource.location(),
                createFieldResource.area()
        );

        var field = fieldCommandService.handle(createFieldCommand);

        if (field.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(field.get());

        return  ResponseEntity.status(HttpStatus.CREATED).body(fieldResource);
    }

    @PutMapping(value = "/me")
    public ResponseEntity<FieldResource> updateField(@RequestBody UpdateFieldResource updateFieldResource) {
        var userId = iamContextFacade.getCurrentUserId();

        var updateFieldCommand = new UpdateFieldDataCommand(
                userId,
                updateFieldResource.fieldName(),
                updateFieldResource.location(),
                updateFieldResource.area()
        );

        var field = fieldCommandService.handle(updateFieldCommand);

        if (field.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(field.get());

        return  ResponseEntity.status(HttpStatus.OK).body(fieldResource);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<FieldResource> getField() {
        var userId = iamContextFacade.getCurrentUserId();

        var getFieldCommand = new GetFieldByFarmerIdQuery(userId);

        var field = fieldQueryService.handle(getFieldCommand);

        if (field.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(field.get());

        return  ResponseEntity.status(HttpStatus.OK).body(fieldResource);
    }

    @GetMapping(value = "/cooperative/{organizationId}/members")
    public ResponseEntity<List<MemberFieldResource>> getMembersField(
            @PathVariable("organizationId") UUID organizationId
    ){

        var members = organizationManagementFacade.getMembersByCooperativeId(organizationId);

        if (members == null || members.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var memberFields = members.stream()
                .map(member -> {
                    var getFieldCommand = new GetFieldByFarmerIdQuery(member.getUserId());

                    var fieldResourceOptional = fieldQueryService.handle(getFieldCommand);

                    if (fieldResourceOptional.isPresent()) {
                        var fieldResource = fieldResourceOptional.get();

                        return new MemberFieldResource(
                                fieldResource.getId(),
                                fieldResource.getFieldName(),
                                fieldResource.getLocation(),
                                fieldResource.getTotalArea(),
                                fieldResource.getStatus(),
                                member.getId(),
                                member.getFullName()
                        );
                    } else {
                        return new MemberFieldResource(
                                null,
                                "No Field Assigned",
                                null,
                                null,
                                null,
                                member.getId(),
                                member.getFullName()
                        );
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(memberFields);

    }

    @PatchMapping(value = "/{fieldId}/status")
    public ResponseEntity<FieldResource> updateFieldStatus(
            @PathVariable("fieldId") UUID fieldId,
            @Valid @RequestBody UpdateFieldStatusResource request) {
        var userId = iamContextFacade.getCurrentUserId();
        var updateFieldCommand = new UpdateFieldStatusCommand(fieldId, userId,request.status());

        var updatedField = fieldCommandService.handle(updateFieldCommand);

        return updatedField.map(field -> ResponseEntity.ok(FieldResourceFromEntityAssembler.toResourceFromEntity(field)))
                .orElse(ResponseEntity.notFound().build());
    }



}
