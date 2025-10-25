package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByIdQuery;

import java.util.Optional;

public interface CooperativeQueryService {
    Optional<Cooperative> handle(GetCooperativeByIdQuery query);
}
