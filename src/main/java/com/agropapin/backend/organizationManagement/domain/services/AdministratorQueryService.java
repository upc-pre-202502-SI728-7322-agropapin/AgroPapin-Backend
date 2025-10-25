package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByUserIdAsyncQuery;

import java.util.Optional;

public interface AdministratorQueryService {
    Optional<Administrator> handle(GetAdministratorByIdQuery getAdministratorByIdQuery);
    Optional<Administrator> handle(GetAdministratorByUserIdAsyncQuery getAdministratorByUserIdAsyncQuery);
}
