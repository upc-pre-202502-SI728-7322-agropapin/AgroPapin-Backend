package com.agropapin.backend.organizationManagement.interfaces.acl;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateAdministratorCommand;
import com.agropapin.backend.organizationManagement.domain.model.commands.CreateFarmerCommand;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetAdministratorByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetCooperativeByIdQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetFarmerByUserIdAsyncQuery;
import com.agropapin.backend.organizationManagement.domain.model.queries.GetMembersByCooperativeId;
import com.agropapin.backend.organizationManagement.domain.services.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrganizationManagementFacade {
    private final FarmerQueryService farmerQueryService;
    private final FarmerCommandService farmerCommandService;
    private final AdministratorQueryService administratorQueryService;
    private final AdministratorCommandService administratorCommandService;
    private final CooperativeQueryService cooperativeQueryService;

    public OrganizationManagementFacade(FarmerQueryService farmerQueryService, FarmerCommandService farmerCommandService, AdministratorQueryService administratorQueryService, AdministratorCommandService administratorCommandService, CooperativeQueryService cooperativeQueryService){
        this.farmerQueryService = farmerQueryService;
        this.farmerCommandService = farmerCommandService;
        this.administratorQueryService = administratorQueryService;
        this.administratorCommandService = administratorCommandService;
        this.cooperativeQueryService = cooperativeQueryService;
    }

    public Farmer getFarmerByUserId(String userId){
        var getFarmerByUserIdQuery = new GetFarmerByUserIdAsyncQuery(userId);
        var farmer = this.farmerQueryService.handle(getFarmerByUserIdQuery);
        return farmer.orElse(null);
    }

    public void createFarmer(String email, String userId){
        var createFarmerCommand = new CreateFarmerCommand(email, userId);
        var farmer = this.farmerCommandService.handle(createFarmerCommand);
    }

    public Administrator getAdministratorByUserId(String userId){
        var getAdministratorByUserIdQuery = new GetAdministratorByUserIdAsyncQuery(userId);
        var administrator = this.administratorQueryService.handle(getAdministratorByUserIdQuery);
        return administrator.orElse(null);
    }

    public void createAdministrator(String email, String userId){
        var createAdministratorCommand = new CreateAdministratorCommand(email, userId);
        var administrator = this.administratorCommandService.handle(createAdministratorCommand);
    }

    public List<Farmer> getMembersByCooperativeId(UUID cooperativeId){
        var getMembersByCooperativeId = new GetCooperativeByIdQuery(cooperativeId);
        var cooperative = this.cooperativeQueryService.handle(getMembersByCooperativeId);

        return cooperative.map(Cooperative::getMembers).orElse(null);
    }

    public Cooperative getCooperativeByAdministratorUserId(String adminUserId) {
        var adminQuery = new GetAdministratorByUserIdAsyncQuery(adminUserId);
        var admin = this.administratorQueryService.handle(adminQuery)
                .orElseThrow(() -> new RuntimeException("Administrator not found for User ID: " + adminUserId));

        UUID coopId = admin.getCooperative().getId();

        // 3. Buscamos la cooperativa
        var coopQuery = new GetCooperativeByIdQuery(coopId);
        return this.cooperativeQueryService.handle(coopQuery)
                .orElseThrow(() -> new RuntimeException("Cooperative not found"));
    }
}
