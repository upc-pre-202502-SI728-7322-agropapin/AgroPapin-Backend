package com.agropapin.backend.telemetryingestion.infrastructure.persistence.repositories;

import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.ChartDataResource;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryReader;
import com.agropapin.backend.telemetryingestion.interfaces.rest.resources.AvgReadingResource;
import com.influxdb.client.InfluxDBClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InfluxDbTelemetryReader implements TelemetryReader {

    private final InfluxDBClient influxClient;
    private final String bucket;

    public InfluxDbTelemetryReader(InfluxDBClient influxClient,
                                   @Value("${influxdb.bucket}") String bucket) {
        this.influxClient = influxClient;
        this.bucket = bucket;
    }

    @Override
    public List<AvgReadingResource> getLatestMetrics(String orgId, String plotId) {
        String fluxQuery = buildFluxQuery(plotId);

        List<FluxTable> tables = influxClient.getQueryApi().query(fluxQuery, orgId);

        return mapFluxResultToResource(tables);
    }

    /**
     * Obtiene datos históricos agregados (promediados) para gráficos.
     * @param orgId El ID de la organización
     * @param plotId El ID de la parcela (OBLIGATORIO para esta consulta)
     * @param days Cuántos días hacia atrás (ej. 7 días)
     * @return Lista de puntos de datos para el gráfico
     */
    @Override
    public List<ChartDataResource> getHistoricalMetrics(String orgId, String plotId, int days) {

        String windowInterval = "1h";

        String fluxQuery = String.format("""
            from(bucket: "%s")
              |> range(start: -%dd) // 1. Rango dinámico (ej. -7d)
              |> filter(fn: (r) => r["_measurement"] == "plot_reading")
              |> filter(fn: (r) => r["plotId"] == "%s") // 2. Filtro de PlotId (obligatorio)
              |> filter(fn: (r) => r["_field"] == "temperature" or r["_field"] == "humidity" or r["_field"] == "soilMoisture")
              
              // 3. Agrega los datos en "ventanas" (ej. 1 hora) y calcula el promedio
              |> aggregateWindow(every: %s, fn: mean, createEmpty: false)
              
              // 4. Pivotea los datos:
              //   Queremos filas por tiempo, y columnas por 'field'
              //   Resultado: { _time: ..., temperature: 25.5, humidity: 60.1, ... }
              |> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
              
              |> keep(columns: ["_time", "temperature", "humidity", "soilMoisture"])
              |> yield(name: "historical_metrics")
            """, bucket, days, plotId, windowInterval);

        List<FluxTable> tables = influxClient.getQueryApi().query(fluxQuery, orgId);

        return mapToChartData(tables);
    }

    private String buildFluxQuery(String plotId) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(String.format("from(bucket: \"%s\")\n", bucket));
        queryBuilder.append("  |> range(start: -10m)\n");
        queryBuilder.append("  |> filter(fn: (r) => r[\"_measurement\"] == \"plot_reading\")\n");

        if (plotId != null && !plotId.isEmpty()) {
            queryBuilder.append(String.format("  |> filter(fn: (r) => r[\"plotId\"] == \"%s\")\n", plotId));
        }

//        queryBuilder.append("  |> group(columns: [\"plotId\", \"serialNumber\"])\n");
//        queryBuilder.append("  |> mean()\n");
        queryBuilder.append("  |> filter(fn: (r) => r[\"_field\"] == \"temperature\" or r[\"_field\"] == \"humidity\" or r[\"_field\"] == \"soilMoisture\")\n");
        queryBuilder.append("  |> last()\n");

        queryBuilder.append("  |> keep(columns: [\"plotId\", \"serialNumber\", \"_field\", \"_value\", \"_time\"])\n");
        queryBuilder.append("  |> yield(name: \"latest_metrics\")");

        return queryBuilder.toString();
    }

    /**
     * Mapea los resultados de Flux (lista de tablas) a la lista de DTOs agregados.
     * Esta implementación recolecta los campos (fields) antes de construir el 'record'.
     */
    private List<AvgReadingResource> mapFluxResultToResource(List<FluxTable> tables) {

        Map<UUID, Map<String, Object>> recordsBuilder = new HashMap<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {

                UUID plotId = UUID.fromString((String) record.getValueByKey("plotId"));
                String serialNumber = (String) record.getValueByKey("serialNumber");
                Instant timestamp = record.getTime();

                Map<String, Object> builder = recordsBuilder.computeIfAbsent(plotId, k -> new HashMap<>());

                builder.put("serialNumber", serialNumber);
                if (timestamp != null) {
                    builder.put("lastUpdated", timestamp.toString());
                }

                String field = record.getField();
                Object valueObject = record.getValue();

                float valueAsFloat = 0.0f;

                if (valueObject instanceof Double) {
                    valueAsFloat = ((Double) valueObject).floatValue();
                } else if (valueObject instanceof Long) {
                    valueAsFloat = ((Long) valueObject).floatValue();
                } else if (valueObject instanceof String) {
                    try {
                        valueAsFloat = Float.parseFloat((String) valueObject);
                    } catch (NumberFormatException e) {
                    }
                }

                if (field != null) {
                    switch (field) {
                        case "temperature":
                            builder.put("avgTemperature", valueAsFloat);
                            break;
                        case "humidity":
                            builder.put("avgHumidity", valueAsFloat);
                            break;
                        case "soilMoisture":
                            builder.put("avgSoilMoisture", valueAsFloat);
                            break;
                    }
                }
            }
        }

        return recordsBuilder.entrySet().stream()
                .map(entry -> {
                    UUID plotId = entry.getKey();
                    Map<String, Object> values = entry.getValue();

                    return new AvgReadingResource(
                            plotId,
                            (String) values.get("serialNumber"),
                            (float) values.getOrDefault("avgTemperature", 0.0f),
                            (float) values.getOrDefault("avgHumidity", 0.0f),
                            (float) values.getOrDefault("avgSoilMoisture", 0.0f),
                            (String) values.getOrDefault("lastUpdated", Instant.now().toString())
                    );
                })
                .collect(Collectors.toList());
    }

    private List<ChartDataResource> mapToChartData(List<FluxTable> tables) {
        List<ChartDataResource> chartData = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {

                Instant time = record.getTime();

                // Extraer valores de las columnas pivotadas de forma segura
                Float temp = getFloatFromRecord(record, "temperature");
                Float hum = getFloatFromRecord(record, "humidity");
                Float soil = getFloatFromRecord(record, "soilMoisture");

                chartData.add(new ChartDataResource(time, temp, hum, soil));
            }
        }
        return chartData;
    }

    private Float getFloatFromRecord(FluxRecord record, String fieldName) {
        Object value = record.getValueByKey(fieldName);
        if (value == null) return 0.0f; // O null, según prefieras

        if (value instanceof Double) {
            return ((Double) value).floatValue();
        } else if (value instanceof Long) {
            return ((Long) value).floatValue();
        }
        return 0.0f;
    }
}
