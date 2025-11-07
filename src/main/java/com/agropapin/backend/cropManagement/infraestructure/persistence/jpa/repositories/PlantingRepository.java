package com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Planting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlantingRepository extends JpaRepository<Planting, UUID> {
    List<Planting> findAllByPlotId(UUID plotId);
}
