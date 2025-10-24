package com.agropapin.backend.organizationManagement.domain.services;

import java.util.List;
import java.util.Optional;

public interface DeveloperQueryService {
    List<Developer> handle(GetAllDevelopersAsyncQuery query);
    Optional<Developer> handle(GetDeveloperByUserIdAsyncQuery query);
    Optional<Developer> handle(GetDeveloperByIdQuery query);

}
