package com.agropapin.backend.cropManagement.application.internal.commandservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Product;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateProductCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeleteProductCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateProductCommand;
import com.agropapin.backend.cropManagement.domain.model.services.ProductCommandService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.PlotRepository;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final PlotRepository plotRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository, PlotRepository plotRepository) {
        this.productRepository = productRepository;
        this.plotRepository = plotRepository;
    }

    @Override
    public Optional<Product> handle(CreateProductCommand command) {
        var plot = plotRepository.findById(command.plotId())
                .orElseThrow(() -> new IllegalArgumentException("Plot not found"));

        var product = new Product(
                command.name(),
                command.applicationDate(),
                command.type(),
                command.quantity(),
                command.plantingId(),
                plot
        );
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> handle(UpdateProductCommand command) {
        return productRepository.findById(command.productId()).map(product -> {
            product.update(command.name(), command.type(), command.quantity());
            productRepository.save(product);
            return product;
        });
    }

    @Override
    public boolean handle(DeleteProductCommand command) {
        return productRepository.findById(command.productId()).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }
}
