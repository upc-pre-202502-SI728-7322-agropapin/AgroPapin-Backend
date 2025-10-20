package com.agropapin.backend.organizationManagement.domain.services;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Developer;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAllDevelopersAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetDeveloperByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetDeveloperByUserIdAsyncQuery;

import java.util.List;
import java.util.Optional;

public interface DeveloperQueryService {
    List<Developer> handle(GetAllDevelopersAsyncQuery query);
    Optional<Developer> handle(GetDeveloperByUserIdAsyncQuery query);
    Optional<Developer> handle(GetDeveloperByIdQuery query);

}
