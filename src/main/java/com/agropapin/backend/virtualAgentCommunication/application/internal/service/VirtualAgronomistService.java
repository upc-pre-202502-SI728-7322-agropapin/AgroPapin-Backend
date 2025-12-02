package com.agropapin.backend.virtualAgentCommunication.application.internal.service;

import com.agropapin.backend.cropManagement.interfaces.acl.PlotManagementFacade;
import com.agropapin.backend.cropManagement.interfaces.acl.ExternalCropManagementFacade;
import com.agropapin.backend.telemetryingestion.application.internal.queryservices.TelemetryQueryServiceImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VirtualAgronomistService {
    private final ChatClient chatClient;
    private final TelemetryQueryServiceImpl telemetryQueryService;
    private final ExternalCropManagementFacade cropManagementFacade;
    private final PlotManagementFacade plotManagementFacade;

    public VirtualAgronomistService(ChatClient.Builder chatClientBuilder, TelemetryQueryServiceImpl telemetryQueryService, ExternalCropManagementFacade cropManagementFacade, PlotManagementFacade plotManagementFacade) {
        this.chatClient = chatClientBuilder.build();
        this.telemetryQueryService = telemetryQueryService;
        this.cropManagementFacade = cropManagementFacade;
        this.plotManagementFacade = plotManagementFacade;
    }

    public String askAgent(String userQuestion, UUID fieldId, UUID plotId) {

        String systemPrompt = "";
        String dataContext = "";

        if (plotId != null) {
            var metrics = telemetryQueryService.getLatestAggregatedMetrics(plotId.toString());
            var cropInfo = cropManagementFacade.getCropInfo(plotId);

            dataContext = String.format("""
                DATOS ESPECÍFICOS PARCELA %s:
                - Cultivo: %s
                - Sensores: %s
                """, plotId, cropInfo, metrics);

            systemPrompt = """
                Eres PapinBot, un agrónomo experto analizando UNA parcela específica.
                Usa los datos de sensores para diagnosticar. Si la humedad < 30, sugiere riego.
                """;

        } else {
            var plotsSummary = plotManagementFacade.getPlotsSummaryByFieldId(fieldId);

            dataContext = String.format("""
                RESUMEN DE PARCELAS DEL AGRICULTOR:
                %s
                """, plotsSummary); // plotsSummary.toString() debe ser breve

            systemPrompt = """
                Eres PapinBot, asistente general de la granja.
                Tienes un resumen de las parcelas.
                1. Si el usuario pregunta "cómo está todo", dales un resumen breve.
                2. Si ves una parcela en estado "ALERTA", avísale y sugiérele que pregunte por esa parcela específicamente para ver detalles.
                3. NO inventes datos de sensores si no los tienes.
                """;
        }

        // Combinar en la llamada
        String finalDataContext = dataContext;
        String finalSystemPrompt = systemPrompt;

        return chatClient.prompt()
                .system(s -> s.text(finalSystemPrompt + "\nCONTEXTO:\n" + finalDataContext))
                .user(userQuestion)
                .call()
                .content();
    }
}
