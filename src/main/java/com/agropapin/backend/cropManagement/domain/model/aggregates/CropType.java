package com.agropapin.backend.cropManagement.domain.model.aggregates;

import com.agropapin.backend.cropManagement.domain.model.valueObjects.GrowthProfile;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.IdealConditions;
import com.agropapin.backend.cropManagement.domain.model.valueObjects.NutrientNeeds;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropType extends AuditableAbstractAggregateRoot<CropType> {

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Crop type name is mandatory")
    @Size(max = 100, message = "Crop type name must not exceed 100 characters")
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    @NotBlank(message = "Description is mandatory")
    @Size(max = 200, message = "Description must not exceed 100 characters")
    private String description;

    @Column(name = "variaty", nullable = false, length = 100)
    @NotBlank(message = "Variaty is mandatory")
    @Size(max = 100, message = "Variaty must not exceed 100 characters")
    private String variaty;

    @Column(name = "scientific_name", nullable = false, length = 100)
    @NotBlank(message = "Scientific name is mandatory")
    @Size(max = 100, message = "scientific name must not exceed 100 characters")
    private String scientificName;

    @Column(name = "category", nullable = false, length = 100)
    @NotBlank(message = "Category is mandatory")
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;

    @Column(name = "image_Url", nullable = false, length = 300)
    @NotBlank(message = "Image Url is mandatory")
    @Size(max = 100, message = "Image Url  must not exceed 100 characters")
    private String imageUrl;

    @Embedded
    private GrowthProfile growthProfile;

    @Embedded
    private IdealConditions idealConditions;

    @Embedded
    private NutrientNeeds nutrientNeeds;
}
