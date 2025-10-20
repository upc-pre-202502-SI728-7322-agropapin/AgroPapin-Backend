package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long>{
    Optional<Developer> findDeveloperByUser_Id(Long id);
}
