package com.agropapin.backend.organizationManagement.application.internal.commandservices;

import com.agropapin.backend.iam.interfaces.acl.IamContextFacade;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.commands.*;
import com.agropapin.backend.organizationManagement.domain.services.CooperativeCommandService;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.AdministratorRepository;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.CooperativeRepository;
import com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories.FarmerRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CooperativeCommandServiceImpl implements CooperativeCommandService {

    private final CooperativeRepository cooperativeRepository;
    private final AdministratorRepository administratorRepository;
    private final FarmerRepository farmerRepository;

    public CooperativeCommandServiceImpl(CooperativeRepository cooperativeRepository, AdministratorRepository administratorRepository, FarmerRepository farmerRepository, IamContextFacade iamContextFacade) {
        this.cooperativeRepository = cooperativeRepository;
        this.administratorRepository = administratorRepository;
        this.farmerRepository = farmerRepository;
    }

    @Override
    @Transactional
    public Optional<Cooperative> handle(CreateCooperativeCommand createCooperativeCommand) {
        var administrator = administratorRepository.findAdministratorByUserId(createCooperativeCommand.createdByUserId());

        if(administrator.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Administrator found for user id " + createCooperativeCommand.createdByUserId());
        }
        if (administrator.get().getCooperative() != null){
            throw new IllegalArgumentException(
                    "Administrator current have a cooperative"
            );
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
        var cooperative = cooperativeRepository.findByAdministrators_UserId(command.userId());

        if (cooperative.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Cooperative found with any administrator with userId: " + command.userId());
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

        List<Administrator> administrators = cooperative.get().getAdministrators();
        for (Administrator admin : administrators) {
            admin.clearCooperative();
        }

        administratorRepository.saveAll(administrators);

        cooperativeRepository.deleteById(deleteCooperativeCommand.cooperativeId());
        return cooperative;
    }

    @Override
    public Optional<Cooperative> handle(AddNewAdministratorInCooperativeCommand addNewAdministratorInCooperativeCommand) {
        if (cooperativeRepository.existsByIdAndAdministrators_UserId(addNewAdministratorInCooperativeCommand.cooperativeId(), addNewAdministratorInCooperativeCommand.performedByUserId())) {
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
                administrator.get().assignToCooperative(cooperativeData);

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
        if (cooperativeRepository.existsByIdAndAdministrators_UserId(addNewMemberInCooperativeCommand.cooperativeId(), addNewMemberInCooperativeCommand.performedByUserId())) {
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
                farmer.get().assignToCooperative(cooperativeData);

                return cooperativeRepository.save(cooperativeData);
            });
        } else {
            throw new IllegalArgumentException(
                    "User id " + addNewMemberInCooperativeCommand.performedByUserId() +
                            " is not an administrator of cooperative id " + addNewMemberInCooperativeCommand.cooperativeId());
        }
    }
}
