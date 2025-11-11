package com.agropapin.backend.telemetryingestion.domain.interfaces;

import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.AvgReadingResource;

import java.util.List;

public interface TelemetryReader {
    /**
     * Obtiene las métricas promedio de todos los plots para el último minuto.
     * @return Una lista de recursos agregados.
     */
    List<AvgReadingResource> getLatestMetrics(String orgId, String plotId);
}
