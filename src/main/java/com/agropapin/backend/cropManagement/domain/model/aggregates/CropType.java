package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.GrowthProfile;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.IdealConditions;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.NutrientNeeds;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropType extends AuditableAbstractAggregateRoot<CropType> {

    @Column(name = "name", nullable = false, length = 100)
    @Size(max = 100, message = "Crop type name must not exceed 100 characters")
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    @Size(max = 200, message = "Description must not exceed 100 characters")
    private String description;

    @Column(name = "variaty", length = 100)
    @Size(max = 100, message = "Variaty must not exceed 100 characters")
    private String variaty;

    @Column(name = "scientific_name", length = 100)
    @Size(max = 100, message = "scientific name must not exceed 100 characters")
    private String scientificName;

    @Column(name = "category", length = 100)
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;

    @Column(name = "image_Url", length = 300)
    @Size(max = 100, message = "Image Url  must not exceed 100 characters")
    private String imageUrl;

    @Embedded
    private GrowthProfile growthProfile;

    @Embedded
    private IdealConditions idealConditions;

    @Embedded
    private NutrientNeeds nutrientNeeds;

    @OneToMany(
            mappedBy = "cropType",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Planting> planting = new ArrayList<>();
}
