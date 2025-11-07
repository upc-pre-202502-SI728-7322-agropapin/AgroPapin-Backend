package com.agropapin.backend.cropManagement.interfaces.rest.transform;

import com.agropapin.backend.cropManagement.domain.model.aggregates.CropType;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.CropTypeResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.GrowthProfileResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.IdealConditionsResource;
import com.agropapin.backend.cropManagement.interfaces.rest.resources.NutrientNeedsResource;

public class CropTypeResourceFromEntityAssembler {

    public static CropTypeResource toResourceFromEntity(CropType entity) {
        if (entity == null) {
            return null;
        }

        return new CropTypeResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getVariety(),
                entity.getScientificName(),
                entity.getCategory(),
                entity.getImageUrl(),
                mapGrowthProfile(entity),
                mapIdealConditions(entity),
                mapNutrientNeeds(entity),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private static GrowthProfileResource mapGrowthProfile(CropType entity) {
        if (entity.getGrowthProfile() == null) {
            return null;
        }

        return new GrowthProfileResource(
                entity.getGrowthProfile().getEstimatedGrowingDays(),
                entity.getGrowthProfile().getPlantSpacingCm(),
                entity.getGrowthProfile().getRowSpacingCm(),
                entity.getGrowthProfile().getSeedDepthCm()
        );
    }

    private static IdealConditionsResource mapIdealConditions(CropType entity) {
        if (entity.getIdealConditions() == null) {
            return null;
        }

        return new IdealConditionsResource(
                entity.getIdealConditions().getSunlight(),
                entity.getIdealConditions().getMinTemperatureC(),
                entity.getIdealConditions().getMaxTemperatureC(),
                entity.getIdealConditions().getIdealSoilPhMin(),
                entity.getIdealConditions().getIdealSoilPhMax(),
                entity.getIdealConditions().getWateringNeeds()
        );
    }

    private static NutrientNeedsResource mapNutrientNeeds(CropType entity) {
        if (entity.getNutrientNeeds() == null) {
            return null;
        }

        return new NutrientNeedsResource(
                entity.getNutrientNeeds().getNitrogen(),
                entity.getNutrientNeeds().getPhosphorus(),
                entity.getNutrientNeeds().getPotassium()
        );
    }
}
