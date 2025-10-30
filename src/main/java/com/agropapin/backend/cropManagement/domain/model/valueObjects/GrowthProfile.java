package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record GrowthProfile(
        Integer estimatedGrowingDays,
        Float plantSpacingCm,
        Float rowSpacingCm,
        Float seedDepthCm
) {
    public GrowthProfile() {
        this(0, 0f, 0f, 0f);
    }
}
