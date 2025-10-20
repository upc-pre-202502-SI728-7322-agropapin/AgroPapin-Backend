package com.agropapin.backend.organizationManagement.application.internal.queryservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Enterprise;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAllEnterprisesAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetEnterpriseByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetEnterpriseByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.EnterpriseQueryService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.EnterpriseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseQueryServiceImpl implements EnterpriseQueryService {

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseQueryServiceImpl(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    @Override
    public Optional<Enterprise> handle(GetEnterpriseByUserIdAsyncQuery query) {
        return enterpriseRepository.findEnterpriseByUser_Id(query.id());
    }

    @Override
    public Optional<Enterprise> handle(GetEnterpriseByIdQuery query) {
        return this.enterpriseRepository.findById(query.enterpriseId());
    }

    @Override
    public List<Enterprise> handle(GetAllEnterprisesAsyncQuery query) {
        return enterpriseRepository.findAll();
    }
}
