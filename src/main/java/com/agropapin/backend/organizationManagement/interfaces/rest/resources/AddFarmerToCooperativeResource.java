package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

import java.util.UUID;

public record AddFarmerToCooperativeResource(
        UUID farmerUserId
) {
}
