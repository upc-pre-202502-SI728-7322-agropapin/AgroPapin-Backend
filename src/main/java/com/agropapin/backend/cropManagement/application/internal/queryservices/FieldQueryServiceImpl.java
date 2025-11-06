package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Field;
import com.agropapin.backend.cropManagement.domain.model.queries.GetFieldByFarmerIdQuery;
import com.agropapin.backend.cropManagement.domain.model.queries.GetFieldByIdQuery;
import com.agropapin.backend.cropManagement.domain.model.services.FieldQueryService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FieldQueryServiceImpl implements FieldQueryService {

    private final FieldRepository fieldRepository;

    public FieldQueryServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Optional<Field> handle(GetFieldByIdQuery getFieldByIdQuery) {
        return fieldRepository.findById(getFieldByIdQuery.fieldID());
    }

    @Override
    public Optional<Field> handle(GetFieldByFarmerIdQuery getFieldByFarmerIdQuery) {
        return fieldRepository.findFieldByFarmerUserId(getFieldByFarmerIdQuery.farmerId());
    }
}
