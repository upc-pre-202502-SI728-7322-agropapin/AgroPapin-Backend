package com.agropapin.backend.cropManagement.interfaces.rest;

import com.agropapin.backend.cropManagement.domain.model.queries.GetAllCropTypeQuery;
import com.agropapin.backend.cropManagement.domain.model.services.CropTypeQueryService;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.CropTypeResource;
import com.agropapin.backend.cropManagement.interfaces.rest.transform.CropTypeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/crop-types", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Crop Types", description = "Crop Type Management Endpoints")
public class CropTypeController {
    private final CropTypeQueryService cropTypeQueryService;

    public CropTypeController(CropTypeQueryService cropTypeQueryService) {
        this.cropTypeQueryService = cropTypeQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CropTypeResource>> getCropTypes() {
        var getAllCropTypeQuery = new GetAllCropTypeQuery();

        var cropTypes = cropTypeQueryService.handle(getAllCropTypeQuery);

        if (cropTypes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CropTypeResource> cropTypeResources = cropTypes.get()
                .stream()
                .map(CropTypeResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cropTypeResources);
    }
}
