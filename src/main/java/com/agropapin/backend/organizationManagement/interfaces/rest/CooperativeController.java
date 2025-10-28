package com.agropapin.backend.organizationManagement.interfaces.rest;

import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByIdQuery;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeCommandService;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeQueryService;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.CooperativeResource;
import com.agropapin.backend.organizationManagement.interfaces.rest.transform.CooperativeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cooperatives")
@Tag(name = "Cooperatives", description = "Cooperative Management Endpoints")
public class CooperativeController {
    private final CooperativeQueryService cooperativeQueryService;
    private final CooperativeCommandService cooperativeCommandService;

    public CooperativeController(CooperativeQueryService cooperativeQueryService, CooperativeCommandService cooperativeCommandService) {
        this.cooperativeQueryService = cooperativeQueryService;
        this.cooperativeCommandService = cooperativeCommandService;
    }

    @GetMapping(value = "/{cooperativeId}")
    public ResponseEntity<CooperativeResource> getCooperativeById(@PathVariable Long cooperativeId) {
        var getCooperativeByIdQuery = new GetCooperativeByIdQuery(cooperativeId);
        var cooperative = cooperativeQueryService.handle(getCooperativeByIdQuery);
        if (cooperative.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cooperativeResource = CooperativeResourceFromEntityAssembler.toResourceFromEntity(cooperative.get());
        return ResponseEntity.ok(cooperativeResource);
    }
}
