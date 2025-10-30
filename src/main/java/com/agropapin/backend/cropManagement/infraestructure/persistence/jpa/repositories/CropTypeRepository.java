package com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories;

import com.agropapin.backend.cropManagement.domain.model.aggregates.CropType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CropTypeRepository extends JpaRepository<CropType, UUID> {
}
