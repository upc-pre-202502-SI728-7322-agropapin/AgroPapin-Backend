package com.agropapin.backend.organizationManagement.application.internal.queryservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.FarmerQueryService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.FarmerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmerQueryServiceImpl implements FarmerQueryService {

    private final FarmerRepository farmerRepository;

    public FarmerQueryServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    public Optional<Farmer> handle(GetFarmerByIdQuery getFarmerByIdQuery) {
        return farmerRepository.findById(getFarmerByIdQuery.farmerId());
    }

    @Override
    public Optional<Farmer> handle(GetFarmerByUserIdAsyncQuery getFarmerByUserIdAsyncQuery) {
        return farmerRepository.findFarmerByUserId(getFarmerByUserIdAsyncQuery.userId());
    }
}
