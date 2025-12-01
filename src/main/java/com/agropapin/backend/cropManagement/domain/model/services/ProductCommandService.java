package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Product;
import com.agropapin.backend.cropManagement.domain.model.commands.CreateProductCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.DeleteProductCommand;
import com.agropapin.backend.cropManagement.domain.model.commands.UpdateProductCommand;

import java.util.Optional;

public interface ProductCommandService {
    Optional<Product> handle(CreateProductCommand command);
    Optional<Product> handle(UpdateProductCommand command);
    boolean handle(DeleteProductCommand command);
}
