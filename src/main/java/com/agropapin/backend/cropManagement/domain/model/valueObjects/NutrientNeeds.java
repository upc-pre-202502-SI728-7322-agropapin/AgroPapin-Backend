package com.agropapin.backend.cropManagement.domain.model.valueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NutrientNeeds {

    @Column(name = "nitrogen", length = 50)
    private String nitrogen;

    @Column(name = "phosphorus", length = 50)
    private String phosphorus;

    @Column(name = "potassium", length = 50)
    private String potassium;
}