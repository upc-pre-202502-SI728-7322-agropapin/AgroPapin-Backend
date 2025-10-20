package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Enterprise;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAllEnterprisesAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetEnterpriseByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetEnterpriseByUserIdAsyncQuery;

import java.util.List;
import java.util.Optional;

public interface EnterpriseQueryService {
    Optional<Enterprise> handle(GetEnterpriseByUserIdAsyncQuery query);
    Optional<Enterprise> handle(GetEnterpriseByIdQuery query);
    List<Enterprise> handle(GetAllEnterprisesAsyncQuery query);
}
