package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Optional<Enterprise> findEnterpriseByUser_Id(Long id);
}
