package com.agropapin.backend.cropManagement.infraestructure.persistence.jpa.repositories;

import com.agropapin.backend.cropManagement.domain.model.aggregates.IrrigationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IrrigationPolicyRepository extends JpaRepository<IrrigationPolicy, UUID> {
    Optional<IrrigationPolicy> findByCropTypeId(UUID cropTypeId);
}
