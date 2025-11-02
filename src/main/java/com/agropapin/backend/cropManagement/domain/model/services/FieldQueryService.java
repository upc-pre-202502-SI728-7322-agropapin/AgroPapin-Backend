package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.domain.model.queries.GetFieldByFarmerIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetFieldByIdQuery;

import java.util.Optional;

public interface FieldQueryService {
    Optional<Field> handle(GetFieldByIdQuery getFieldByIdQuery);
    Optional<Field> handle(GetFieldByFarmerIdQuery getFieldByFarmerIdQuery);
}
