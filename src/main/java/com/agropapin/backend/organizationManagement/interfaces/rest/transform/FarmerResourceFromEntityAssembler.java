package com.agropapin.backend.organizationManagement.interfaces.rest.transform;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.interfaces.rest.resources.FarmerResource;

public class FarmerResourceFromEntityAssembler {
    public static FarmerResource toResourceFromEntity(Farmer entity) {
        return new  FarmerResource(
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
