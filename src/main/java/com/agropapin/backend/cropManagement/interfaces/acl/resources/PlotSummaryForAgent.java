package com.agropapin.backend.cropManagement.interfaces.acl.resources;

public record PlotSummaryForAgent(
        String plotId,
        String plotName,
        String status,
        String currentCrop,
        String plantingDate,
        String areaInfo
) {
    @Override
    public String toString() {
        String cropInfo = (currentCrop != null)
                ? String.format("Cultivo: %s (Sembrado: %s)", currentCrop, plantingDate)
                : "Sin cultivo activo";

        return String.format("""
            - [ID: %s] %s (%s): %s. Estado: %s.
            """, plotId, plotName, areaInfo, cropInfo, status);
    }
}
