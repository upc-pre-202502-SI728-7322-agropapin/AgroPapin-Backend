package com.agropapin.backend.cropManagement.interfaces.rest;

import com.agropapin.backend.cropManagement.domain.model.commands.CreateProductCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeleteProductCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateProductCommand;
import com.agropapin.backend.cropManagement.domain.model.queries.GetProductsByPlantingIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetProductsByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.ProductCommandService;
import com.agropapin.backend.cropManagement.domain.model.services.ProductQueryService;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.Quantity;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.CreateProductResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.ProductResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.UpdateProductResource;
import com.agropapin.backend.cropManagement.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/plots/{plotId}/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Product Application Management Endpoints")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @PostMapping
    public ResponseEntity<ProductResource> createProduct(@PathVariable UUID plotId, @RequestBody CreateProductResource resource) {
        var command = new CreateProductCommand(
                resource.name(),
                resource.applicationDate(),
                resource.type(),
                new Quantity(resource.amount(), resource.unit()),
                resource.plantingId(),
                plotId
        );
        var product = productCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Error creating product"));
        
        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product);
        return new ResponseEntity<>(productResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResource>> getProductsByPlotId(@PathVariable UUID plotId) {
        var query = new GetProductsByPlotIdQuery(plotId);
        var products = productQueryService.handle(query);
        var productResources = products.stream()
                .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResources);
    }

    @GetMapping("/planting/{plantingId}")
    public ResponseEntity<List<ProductResource>> getProductsByPlantingId(@PathVariable UUID plotId, @PathVariable UUID plantingId){
        var query = new GetProductsByPlantingIdQuery(plantingId);
        var products = productQueryService.handle(query);
        var productResources = products.stream()
                .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResources);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResource> updateProduct(@PathVariable UUID plotId, @PathVariable UUID productId, @RequestBody UpdateProductResource resource) {
        var command = new UpdateProductCommand(
                productId,
                resource.name(),
                resource.type(),
                new Quantity(resource.amount(), resource.unit())
        );
        var product = productCommandService.handle(command)
                .orElseThrow(() -> new IllegalArgumentException("Product not found or error updating"));

        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product);
        return ResponseEntity.ok(productResource);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID plotId, @PathVariable UUID productId) {
        var command = new DeleteProductCommand(productId);
        if (!productCommandService.handle(command)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
