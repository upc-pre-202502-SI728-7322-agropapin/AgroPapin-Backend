package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByUserIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetMembersByCooperativeId;

import java.util.List;
import java.util.Optional;

public interface CooperativeQueryService {
    Optional<Cooperative> handle(GetCooperativeByIdQuery query);
    Optional<Cooperative> handle(GetCooperativeByUserIdQuery query);
}
