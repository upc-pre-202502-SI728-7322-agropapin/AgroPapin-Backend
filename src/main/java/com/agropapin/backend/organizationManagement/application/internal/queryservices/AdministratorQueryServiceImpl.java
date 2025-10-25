package com.agropapin.backend.organizationManagement.application.internal.queryservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorQueryService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministratorQueryServiceImpl implements AdministratorQueryService {

    private final AdministratorRepository administratorRepository;

    public AdministratorQueryServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public Optional<Administrator> handle(GetAdministratorByIdQuery getAdministratorByIdQuery) {
        return administratorRepository.findById(getAdministratorByIdQuery.administratorId());
    }

    @Override
    public Optional<Administrator> handle(GetAdministratorByUserIdAsyncQuery getAdministratorByUserIdAsyncQuery) {
        return administratorRepository.findAdministratorByUser_Id(getAdministratorByUserIdAsyncQuery.userId());
    }
}
