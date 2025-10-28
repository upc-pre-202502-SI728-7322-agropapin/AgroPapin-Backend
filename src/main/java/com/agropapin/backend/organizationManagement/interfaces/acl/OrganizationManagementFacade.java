package com.agropapin.backend.organizationManagement.interfaces.acl;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateAdministratorCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateFarmerCommand;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorCommandService;
import com.agropapin.backend.organizationManagement.domain.services.AdministratorQueryService;
import com.agropapin.backend.organizationManagement.domain.services.FarmerCommandService;
import com.agropapin.backend.organizationManagement.domain.services.FarmerQueryService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationManagementFacade {
    private final FarmerQueryService farmerQueryService;
    private final FarmerCommandService farmerCommandService;
    private final AdministratorQueryService administratorQueryService;
    private final AdministratorCommandService administratorCommandService;

    public OrganizationManagementFacade(FarmerQueryService farmerQueryService, FarmerCommandService farmerCommandService, AdministratorQueryService administratorQueryService, AdministratorCommandService administratorCommandService){
        this.farmerQueryService = farmerQueryService;
        this.farmerCommandService = farmerCommandService;
        this.administratorQueryService = administratorQueryService;
        this.administratorCommandService = administratorCommandService;
    }

    public Farmer getFarmerByUserId(UUID userId){
        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);
        var farmer = this.farmerQueryService.handle(getFarmerByUserIdQuery);
        return farmer.orElse(null);
    }

    public void createFarmer(String firstName, String lastName, String country, String phone, UUID userId){
        var createFarmerCommand = new CreateFarmerCommand(firstName, lastName, country, phone, userId);
        var farmer = this.farmerCommandService.handle(createFarmerCommand);
    }

    public Administrator getAdministratorByUserId(UUID userId){
        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);
        var administrator = this.administratorQueryService.handle(getAdministratorByUserIdQuery);
        return administrator.orElse(null);
    }

    public void createAdministrator(String firstName, String lastName, String country, String phone, UUID userId){
        var createAdministratorCommand = new CreateAdministratorCommand(firstName, lastName, country, phone, userId);
        var administrator = this.administratorCommandService.handle(createAdministratorCommand);
    }
}
