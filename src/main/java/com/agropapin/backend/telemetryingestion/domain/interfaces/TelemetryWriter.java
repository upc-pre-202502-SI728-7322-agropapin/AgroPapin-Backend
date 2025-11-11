package com.agropapin.backend.telemetryingestion.domain.interfaces;

import com.agropapin.backend.telemetryingestion.domain.model.valueobjects.PlotReading;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TelemetryWriter {

    /**
     * Escribe un lote (batch) de lecturas de telemetría.
     * @param readings La lista de modelos de lectura a guardar.
     */
    void writeBatch(List<PlotReading> readings);

}
