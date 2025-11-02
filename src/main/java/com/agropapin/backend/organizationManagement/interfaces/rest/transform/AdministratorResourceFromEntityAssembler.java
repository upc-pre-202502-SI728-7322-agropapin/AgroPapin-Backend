package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Administrator;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.AdministratorResource;

public class AdministratorResourceFromEntityAssembler {
    public static AdministratorResource toResourceFromEntity(Administrator entity) {
        return new AdministratorResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getCountry(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getUserId(),
                entity.getCooperative() == null ? null : entity.getCooperative().getId()
        );
    }
}
