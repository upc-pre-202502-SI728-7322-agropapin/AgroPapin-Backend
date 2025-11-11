package com.agropapin.backend.cropManagement.interfaces.rest.resources;

import java.util.Date;
import java.util.UUID;

public record CropTypeResource(
        UUID id,
        String name,
        String description,
        String variety,
        String scientificName,
        String category,
        String imageUrl,
        GrowthProfileResource growthProfile,
        IdealConditionsResource idealConditions,
        NutrientNeedsResource nutrientNeeds,
        Date createdAt,
        Date updatedAt
) {
}

