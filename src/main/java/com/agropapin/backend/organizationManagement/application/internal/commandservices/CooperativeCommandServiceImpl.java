package com.agropapin.backend.organizationManagement.application.internal.commandservices;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.commands.*;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeCommandService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.AdministratorRepository;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.CooperativeRepository;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.FarmerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CooperativeCommandServiceImpl implements CooperativeCommandService {

    private final CooperativeRepository cooperativeRepository;
    private final AdministratorRepository administratorRepository;
    private final FarmerRepository farmerRepository;

    public CooperativeCommandServiceImpl(CooperativeRepository cooperativeRepository, AdministratorRepository administratorRepository, FarmerRepository farmerRepository) {
        this.cooperativeRepository = cooperativeRepository;
        this.administratorRepository = administratorRepository;
        this.farmerRepository = farmerRepository;
    }

    @Override
    public Optional<Cooperative> handle(CreateCooperativeCommand createCooperativeCommand) {
        var administrator = administratorRepository.findAdministratorByUserId(createCooperativeCommand.createdByUserId());

        if(administrator.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Administrator found for user id " + createCooperativeCommand.createdByUserId());
        }

        var cooperative = new Cooperative(
                createCooperativeCommand.name(),
                administrator.get()
        );

        var savedCooperative = cooperativeRepository.save(cooperative);
        return Optional.of(savedCooperative);
    }

    @Override
    public Optional<Cooperative> handle(UpdateCooperativeCommand command) {
        var cooperative = cooperativeRepository.findById(command.cooperativeId());

        if (cooperative.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Cooperative found with id " + command.cooperativeId());
        }

        return cooperative.map(cooperativeData -> {
            cooperativeData.updateInfo(
                    command.name()
            );
            return cooperativeRepository.save(cooperativeData);
        });
    }

    @Override
    public Optional<Cooperative> handle(DeleteCooperativeCommand deleteCooperativeCommand) {
        var cooperative = cooperativeRepository.findById(deleteCooperativeCommand.cooperativeId());

        if (cooperative.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Cooperative found with id " + deleteCooperativeCommand.cooperativeId());
        }

        cooperativeRepository.deleteById(deleteCooperativeCommand.cooperativeId());
        return cooperative;
    }

    @Override
    public Optional<Cooperative> handle(AddNewAdministratorInCooperativeCommand addNewAdministratorInCooperativeCommand) {
        if (cooperativeRepository.existsByIdAndAdministrators_Id(addNewAdministratorInCooperativeCommand.cooperativeId(), addNewAdministratorInCooperativeCommand.performedByUserId())) {
            var administrator = administratorRepository.findAdministratorByUserId(addNewAdministratorInCooperativeCommand.newAdministratorId());

            if (administrator.isEmpty()) {
                throw new IllegalArgumentException(
                        "No Administrator found for user id " + addNewAdministratorInCooperativeCommand.newAdministratorId());
            }

            var cooperative = cooperativeRepository.findById(addNewAdministratorInCooperativeCommand.cooperativeId());

            if (cooperative.isEmpty()) {
                throw new IllegalArgumentException(
                        "No Cooperative found with id " + addNewAdministratorInCooperativeCommand.cooperativeId());
            }

            return cooperative.map(cooperativeData -> {
                cooperativeData.addAdministrator(administrator.get());
                return cooperativeRepository.save(cooperativeData);
            });
        } else {
            throw new IllegalArgumentException(
                    "User id " + addNewAdministratorInCooperativeCommand.performedByUserId() +
                            " is not an administrator of cooperative id " + addNewAdministratorInCooperativeCommand.cooperativeId());
        }
    }

    @Override
    public Optional<Cooperative> handle(AddNewMemberInCooperativeCommand addNewMemberInCooperativeCommand) {
        if (cooperativeRepository.existsByIdAndAdministrators_Id(addNewMemberInCooperativeCommand.cooperativeId(), addNewMemberInCooperativeCommand.performedByUserId())) {
            var farmer = farmerRepository.findFarmerByUserId(addNewMemberInCooperativeCommand.newMemberId());

            if (farmer.isEmpty()) {
                throw new IllegalArgumentException(
                        "No Farmer found for user id " + addNewMemberInCooperativeCommand.newMemberId());
            }

            var cooperative = cooperativeRepository.findById(addNewMemberInCooperativeCommand.cooperativeId());

            if (cooperative.isEmpty()) {
                throw new IllegalArgumentException(
                        "No Cooperative found with id " + addNewMemberInCooperativeCommand.cooperativeId());
            }

            return cooperative.map(cooperativeData -> {
                cooperativeData.addMember(farmer.get());
                return cooperativeRepository.save(cooperativeData);
            });
        } else {
            throw new IllegalArgumentException(
                    "User id " + addNewMemberInCooperativeCommand.performedByUserId() +
                            " is not an administrator of cooperative id " + addNewMemberInCooperativeCommand.cooperativeId());
        }
    }
}
