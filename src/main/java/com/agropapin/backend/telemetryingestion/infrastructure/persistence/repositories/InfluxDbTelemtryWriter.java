package com.agropapin.backend.telemetryingestion.infrastructure.persistence.repositories;

import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryWriter;
import com.agropapin.backend.telemetryingestion.domain.model.valueobjects.PlotReading;
import com.agropapin.backend.telemetryingestion.interfaces.rest.TelemetryController;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InfluxDbTelemtryWriter implements TelemetryWriter {

    private final InfluxDBClient influxClient;
    private final String bucket;
    private final String org;
    private static final Logger log = LoggerFactory.getLogger(TelemetryController.class);

    public InfluxDbTelemtryWriter(InfluxDBClient influxClient,
                                  @Value("${influxdb.bucket}") String bucket,
                                  @Value("${influxdb.org}") String org) {
        this.influxClient = influxClient;
        this.bucket = bucket;
        this.org = org;
    }

    @Override
    public void writeBatch(List<PlotReading> readings) {
        WriteApi writeApi = influxClient.getWriteApi();

        List<Point> points = readings.stream()
                .map(this::convertModelToPoint)
                .toList();

        log.info("Writing {} points to InfluxDB.", points.size());
        log.info("Info points: {}", points);

        writeApi.writePoints(bucket, org, points);
    }

    private Point convertModelToPoint(PlotReading model) {
        return Point.measurement("plot_reading")
                .addTag("plotId", String.valueOf(model.plotId()))
                .addTag("serialNumber", model.serialNumber())
                .addField("temperature", model.temperature())
                .addField("humidity", model.humidity())
                .addField("soilMoisture", model.soilMoisture())
                .time(model.timestamp(), WritePrecision.MS);
    }
}
