package com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories;

import com.agropapin.backend.cropManagement.domain.model.aggregates.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ControlRepository extends JpaRepository<Control, UUID> {
    List<Control> findByPlotId(UUID plotId);
    List<Control> findByPlantingId(UUID plantingId);
}
