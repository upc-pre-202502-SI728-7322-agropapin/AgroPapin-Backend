package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;

import java.util.Optional;

public interface FarmerQueryService {
    Optional<Farmer> handle(GetFarmerByIdQuery getFarmerByIdQuery);
    Optional<Farmer> handle(GetFarmerByUserIdAsyncQuery getFarmerByUserIdAsyncQuery);
}
