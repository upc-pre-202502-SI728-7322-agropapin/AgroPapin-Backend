package com.agropapin.backend.telemetryingestion.application.internal.queryservices;

import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryReader;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.AvgReadingResource;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.ChartDataResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelemetryQueryServiceImpl {

    private final TelemetryReader telemetryReader;
    private final String influxOrg;


    public TelemetryQueryServiceImpl(TelemetryReader telemetryReader,
                                     @Value("${influxdb.org}") String org) {
        this.telemetryReader = telemetryReader;
        this.influxOrg = org;
    }

    public List<AvgReadingResource> getLatestAggregatedMetrics(String plotId) {
        return telemetryReader.getLatestMetrics(influxOrg, plotId);
    }

    public List<ChartDataResource> getHistoricalMetrics(String plotId, int days){
        return telemetryReader.getHistoricalMetrics(influxOrg, plotId, days);
    }
}
