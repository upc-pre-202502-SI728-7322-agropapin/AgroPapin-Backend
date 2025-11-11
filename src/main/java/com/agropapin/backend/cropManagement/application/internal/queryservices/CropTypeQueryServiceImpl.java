package com.agropapin.backend.cropManagement.application.internal.queryservices;

import com.agropapin.backend.cropManagement.domain.model.aggregates.CropType;
import com.agropapin.backend.cropManagement.domain.model.queries.GetAllCropTypeQuery;
import com.agropapin.backend.cropManagement.domain.model.services.CropTypeQueryService;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.CropTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropTypeQueryServiceImpl implements CropTypeQueryService {

    private final CropTypeRepository cropTypeRepository;

    public CropTypeQueryServiceImpl(CropTypeRepository cropTypeRepository) {
        this.cropTypeRepository = cropTypeRepository;
    }

    @Override
    public Optional<List<CropType>> handle(GetAllCropTypeQuery getAllCropTypeQuery) {
        List<CropType> cropTypes = cropTypeRepository.findAll();
        return cropTypes.isEmpty() ? Optional.empty() : Optional.of(cropTypes);
    }
}
