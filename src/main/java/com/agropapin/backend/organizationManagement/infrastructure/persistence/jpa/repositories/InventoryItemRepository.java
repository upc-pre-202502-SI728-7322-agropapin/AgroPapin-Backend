package com.agropapin.backend.organizationManagement.infrastructure.persistence.jpa.repositories;

import com.agropapin.backend.organizationManagement.domain.model.aggregates.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID> {
    List<InventoryItem> findByCooperativeId(UUID cooperativeId);
}
