package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GrowthProfile {
    @Column(name = "estimated_growing_days")
    private Integer estimatedGrowingDays;

    @Column(name = "plant_spacing_cm")
    private Float plantSpacingCm;

    @Column(name = "row_spacing_cm")
    private Float rowSpacingCm;

    @Column(name = "seed_depth_cm")
    private Float seedDepthCm;
}
