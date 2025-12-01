package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Product;
import com.agropapin.backend.cropManagement.domain.model.queries.GetProductsByPlantingIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetProductsByPlotIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.ProductQueryService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> handle(GetProductsByPlotIdQuery query) {
        return productRepository.findByPlotId(query.plotId());
    }

    @Override
    public List<Product> handle(GetProductsByPlantingIdQuery query) {
        return productRepository.findByPlantingId(query.plantingId());
    }
}
