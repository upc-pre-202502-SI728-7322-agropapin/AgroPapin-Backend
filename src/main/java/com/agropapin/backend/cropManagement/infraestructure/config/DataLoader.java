package com.agropapin.backend.cropManagement.infraestructure.config;

import com.agropapin.backend.cropManagement.domain.model.aggregates.CropType;
import com.agropapin.backend.cropManagement.domain.model.aggregates.IrrigationPolicy;
import com.agropapin.backend.cropManagement.infraestructure.config.dto.IrrigationPolicyDTO;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.CropTypeRepository;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.IrrigationPolicyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CropTypeRepository cropTypeRepository;
    private final IrrigationPolicyRepository irrigationPolicyRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (cropTypeRepository.count() == 0) {
            log.info("Cargando datos iniciales de CropType...");

            List<CropType> cropTypes = loadCropTypesFromJson();
            List<CropType> savedCropTypes = cropTypeRepository.saveAll(cropTypes);

            log.info("{} CropTypes cargados exitosamente", cropTypes.size());

            log.info("Cargando IrrigationPolicy de riego...");
            List<IrrigationPolicy> irrigationPolicies = loadIrrigationPoliciesFromJson(savedCropTypes);

            irrigationPolicyRepository.saveAll(irrigationPolicies);

            log.info("{} IrrigationPolicies cargados exitosamente", irrigationPolicies.size());
        }
    }

    private List<CropType> loadCropTypesFromJson() {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/data/crop-types.json");

            return objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, CropType.class));

        } catch (Exception e) {
            log.error("Error cargando datos desde JSON", e);
            return List.of();
        }
    }

    private List<IrrigationPolicy> loadIrrigationPoliciesFromJson(List<CropType> cropTypes) {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/data/irrigation-policy.json");

            List<IrrigationPolicyDTO> policyDTOs = objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, IrrigationPolicyDTO.class));

            return policyDTOs.stream()
                    .map(dto -> createIrrigationPolicyEntity(dto, cropTypes))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error cargando políticas de riego desde JSON", e);
            return List.of();
        }
    }

    private IrrigationPolicy createIrrigationPolicyEntity(IrrigationPolicyDTO dto, List<CropType> cropTypes) {
        Optional<CropType> matchingCropType = cropTypes.stream()
                .filter(crop -> crop.getName().equals(dto.getCropName()) &&
                        crop.getVariety().equals(dto.getCropVariety()))
                .findFirst();

        if (matchingCropType.isPresent()) {
            return IrrigationPolicy.builder()
                    .name(dto.getName())
                    .cropName(dto.getCropName())
                    .cropVariety(dto.getCropVariety())
                    .cropTypeId(matchingCropType.get().getId())
                    .rules(dto.getRules())
                    .build();
        } else {
            log.warn("No se encontró CropType para: {} - {}", dto.getCropName(), dto.getCropVariety());
            return null;
        }
    }
}
