package com.agropapin.backend.cropManagement.domain.model.services;

import com.agropapin.backend.cropManagement.domain.model.aggregates.CropType;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllCropTypeQuery;

import java.util.List;
import java.util.Optional;

public interface CropTypeQueryService {
    Optional<List<CropType>> handle(GetAllCropTypeQuery getAllCropTypeQuery);
}
