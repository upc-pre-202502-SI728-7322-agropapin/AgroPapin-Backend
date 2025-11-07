package com.agropapin.backend.cropManagement.infraestructure.config;

import com.agropapin.backend.cropManagement.domain.model.aggregates.CropType;
import com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories.CropTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CropTypeRepository cropTypeRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (cropTypeRepository.count() == 0) {
            log.info("Cargando datos iniciales de CropType...");

            List<CropType> cropTypes = loadCropTypesFromJson();
            cropTypeRepository.saveAll(cropTypes);

            log.info("{} CropTypes cargados exitosamente", cropTypes.size());
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
}
