package com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import com.agropapin.backend.cropManagement.domain.model.enums.CropStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlantingRepository extends JpaRepository<Planting, UUID> {
    List<Planting> findAllByPlotId(UUID plotId);

    @Query("SELECT p FROM Planting p WHERE p.plotId = :plotId AND p.status = 'GROWING' ORDER BY p.plantingDate DESC LIMIT 1")
    Optional<Planting> findTopByPlotIdAndStatusGrowingOrderByPlantingDateDesc(@Param("plotId") UUID plotId);
}
