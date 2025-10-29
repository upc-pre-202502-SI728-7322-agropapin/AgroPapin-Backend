package com.agropapin.backend.organizationManagement.application.internal.queryservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByIdQuery;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeQueryService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.CooperativeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CooperativeQueryServiceImpl implements CooperativeQueryService {

    private final CooperativeRepository cooperativeRepository;

    public CooperativeQueryServiceImpl(CooperativeRepository cooperativeRepository) {
        this.cooperativeRepository = cooperativeRepository;
    }

    @Override
    public Optional<Cooperative> handle(GetCooperativeByIdQuery getCooperativeByIdQuery) {
        return cooperativeRepository.findById(getCooperativeByIdQuery.cooperativeId());
    }
}
