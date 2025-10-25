package com.agropapin.backend.organizationManagement.application.internal.queryservices;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperQueryServiceImpl implements DeveloperQueryService{

    private final DeveloperRepository developerRepository;

    public DeveloperQueryServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }


    @Override
    public List<Developer> handle(GetAllDevelopersAsyncQuery query) {
        return developerRepository.findAll();
    }

    @Override
    public Optional<Developer> handle(GetDeveloperByUserIdAsyncQuery query) {
        return developerRepository.findDeveloperByUser_Id(query.id());
    }

    @Override
    public Optional<Developer> handle(GetDeveloperByIdQuery query) {
        return this.developerRepository.findById(query.developerId());
    }
}
