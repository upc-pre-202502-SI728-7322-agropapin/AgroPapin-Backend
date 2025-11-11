package com.agropapin.backend.telemetryingestion.application.internal.queryservices;

import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryReader;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.AvgReadingResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelemetryQueryServiceImpl {

    private final TelemetryReader telemetryReader;
    private final String influxOrg;

    // 1. Inyección de la Interfaz (Puerto de Dominio)
    public TelemetryQueryServiceImpl(TelemetryReader telemetryReader,
                                     @Value("${influxdb.org}") String org) {
        this.telemetryReader = telemetryReader;
        this.influxOrg = org;
    }

    // El nombre de la clase es TelemetryQueryServiceImpl, pero tu código no lo tenía,
    // por eso lo mantendré como la clase que proporcionaste.

    public List<AvgReadingResource> getLatestAggregatedMetrics(String plotId) {

        // 2. Lógica de negocio (poca aquí, solo la llamada)
        // Llama al puerto sin saber que es InfluxDB
        return telemetryReader.getLatestMetrics(influxOrg, plotId);
    }
}
