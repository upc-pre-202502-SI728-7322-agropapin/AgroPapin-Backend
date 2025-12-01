package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Product;
import com.agropapin.backend.cropManagement.domain.model.queries.GetProductsByPlantingIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetProductsByPlotIdQuery;

import java.util.List;

public interface ProductQueryService {
    List<Product> handle(GetProductsByPlotIdQuery query);
    List<Product> handle(GetProductsByPlantingIdQuery query);
}
