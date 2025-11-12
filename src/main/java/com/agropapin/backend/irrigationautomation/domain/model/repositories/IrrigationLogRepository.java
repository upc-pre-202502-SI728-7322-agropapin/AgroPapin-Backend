package com.agropapin.backend.irrigationautomation.domain.model.repositories;

import com.agropapin.backend.irrigationautomation.domain.model.aggregates.IrrigationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IrrigationLogRepository extends JpaRepository<IrrigationLog, UUID> {
    /**
     * Finds all irrigation logs for a given plot, ordered by the decision timestamp in descending order.
     * @param plotId The ID of the plot.
     * @return A list of irrigation logs.
     */
    List<IrrigationLog> findByPlotIdOrderByDecisionTimestampDesc(UUID plotId);
}
