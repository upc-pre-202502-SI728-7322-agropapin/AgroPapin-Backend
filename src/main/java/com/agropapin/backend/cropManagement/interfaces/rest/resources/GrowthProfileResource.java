package com.agropapin.backend.cropManagement.interfaces.rest.resources;

public record GrowthProfileResource(
        Integer estimatedGrowingDays,
        Float plantSpacingCm,
        Float rowSpacingCm,
        Float seedDepthCm
) {
}
