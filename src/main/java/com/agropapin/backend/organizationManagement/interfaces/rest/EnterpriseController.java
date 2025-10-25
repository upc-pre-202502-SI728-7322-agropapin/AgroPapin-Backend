package com.agropapin.backend.organizationManagement.interfaces.rest;

import com.agropapin.backend.organizationManagement.interfaces.rest.resources.EnterpriseResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.UpdateEnterpriseResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.EnterpriseResourceFromEntityAssembler;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.UpdateEnterpriseCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprises")
@Tag(name = "Enterprises", description = "Enterprise Management Endpoints")
public class EnterpriseController {
    private final EnterpriseQueryService enterpriseQueryService;
    private final EnterpriseCommandService enterpriseCommandService;

    public EnterpriseController(EnterpriseQueryService enterpriseQueryService, EnterpriseCommandService enterpriseCommandService) {
        this.enterpriseQueryService = enterpriseQueryService;
        this.enterpriseCommandService = enterpriseCommandService;
    }

    @GetMapping
    public ResponseEntity<List<EnterpriseResource>> getAllEnterprises() {
        var getAllEnterprisesQuery = new GetAllEnterprisesAsyncQuery();
        var enterprises = enterpriseQueryService.handle(getAllEnterprisesQuery);
        var enterpriseResources = enterprises.stream().map(EnterpriseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(enterpriseResources);
    }

    @GetMapping(value = "/{enterpriseId}")
    public ResponseEntity<EnterpriseResource> getEnterpriseById(@PathVariable Long enterpriseId) {
        var getEnterpriseByIdQuery = new GetEnterpriseByIdQuery(enterpriseId);
        var enterprise = enterpriseQueryService.handle(getEnterpriseByIdQuery);
        if (enterprise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var enterpriseResource = EnterpriseResourceFromEntityAssembler.toResourceFromEntity(enterprise.get());
        return ResponseEntity.ok(enterpriseResource);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<EnterpriseResource> getEnterpriseByUserId(@PathVariable Long userId) {
        var getEnterpriseByUserIdQuery = new GetEnterpriseByUserIdAsyncQuery(userId);
        var enterprise = enterpriseQueryService.handle(getEnterpriseByUserIdQuery);
        if (enterprise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var enterpriseResource = EnterpriseResourceFromEntityAssembler.toResourceFromEntity(enterprise.get());
        return ResponseEntity.ok(enterpriseResource);
    }

    @PutMapping(value = "/{userId}")
        public ResponseEntity<EnterpriseResource> updateEnterpriseByUserId(@PathVariable Long userId, @RequestBody UpdateEnterpriseResource resource) {
        var getEnterpriseByUserIdQuery = new GetEnterpriseByUserIdAsyncQuery(userId);
        var enterprise = enterpriseQueryService.handle(getEnterpriseByUserIdQuery);

        var updateEnterpriseCommand = UpdateEnterpriseCommandFromResourceAssembler.toCommandFromResource(enterprise.get().getUserId(), resource);
        var updatedEnterprise = enterpriseCommandService.handle(updateEnterpriseCommand);
        if (updatedEnterprise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var enterpriseResourceUpdated = EnterpriseResourceFromEntityAssembler.toResourceFromEntity(updatedEnterprise.get());
        return ResponseEntity.ok(enterpriseResourceUpdated);

    }


}
