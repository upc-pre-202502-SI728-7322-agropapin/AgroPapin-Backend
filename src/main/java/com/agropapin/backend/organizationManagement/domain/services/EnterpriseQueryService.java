package com.agropapin.backend.organizationManagement.domain.services;

import java.util.List;
import java.util.Optional;

public interface EnterpriseQueryService {
    Optional<Enterprise> handle(GetEnterpriseByUserIdAsyncQuery query);
    Optional<Enterprise> handle(GetEnterpriseByIdQuery query);
    List<Enterprise> handle(GetAllEnterprisesAsyncQuery query);
}
