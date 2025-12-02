package com.agropapin.backend.virtualAgentCommunication.application.internal.service;

import com.agropapin.backend.cropManagement.interfaces.acl.resources.PlotSummaryForAgent;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Cooperative;
import com.agropapin.backend.organizationManagement.domain.model.aggregates.Farmer;
import com.agropapin.backend.organizationManagement.interfaces.acl.OrganizationManagementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.agropapin.backend.cropManagement.interfaces.acl.PlotManagementFacade;
import com.agropapin.backend.cropManagement.interfaces.acl.ExternalCropManagementFacade;
import com.agropapin.backend.telemetryingestion.application.internal.queryservices.TelemetryQueryServiceImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VirtualAgronomistService {
    private static final Logger log = LoggerFactory.getLogger(VirtualAgronomistService.class);

    private final ChatClient chatClient;
    private final TelemetryQueryServiceImpl telemetryQueryService;
    private final ExternalCropManagementFacade cropManagementFacade;
    private final PlotManagementFacade plotManagementFacade;
    private final OrganizationManagementFacade organizationManagementFacade;

    public VirtualAgronomistService(ChatClient.Builder chatClientBuilder, TelemetryQueryServiceImpl telemetryQueryService, ExternalCropManagementFacade cropManagementFacade, PlotManagementFacade plotManagementFacade, OrganizationManagementFacade organizationManagementFacade) {
        this.chatClient = chatClientBuilder.build();
        this.telemetryQueryService = telemetryQueryService;
        this.cropManagementFacade = cropManagementFacade;
        this.plotManagementFacade = plotManagementFacade;
        this.organizationManagementFacade = organizationManagementFacade;
    }

    public String askAgent(String userQuestion, String role, String userId, UUID fieldId, UUID plotId) {

        String systemPrompt = "";
        String dataContext = "";

        if ("ADMIN".equalsIgnoreCase(role)) {
            return handleAdminInteraction(userQuestion, userId);
        }
        else {
            if (plotId != null) {
                var metrics = telemetryQueryService.getLatestAggregatedMetrics(plotId.toString());
                var cropInfo = cropManagementFacade.getCropInfo(plotId);

                dataContext = String.format("""
                    DATOS ESPECÍFICOS PARCELA %s:
                    - Cultivo: %s
                    - Sensores: %s
                    """, plotId, cropInfo, metrics);

                systemPrompt = """
                    Eres 'PapinBot', un agrónomo experto de campo analizando UNA parcela.
                    Diagnostica problemas inmediatos (riego, plagas).
                    Si la humedad < 30, sugiere riego urgente.
                    """;

            } else {
                var plotsSummary = (fieldId != null)
                        ? plotManagementFacade.getPlotsSummaryByFieldId(fieldId)
                        : "No se especificó un campo (Field ID missing).";

                dataContext = String.format("""
                    RESUMEN DE PARCELAS DEL AGRICULTOR:
                    %s
                    """, plotsSummary);

                systemPrompt = """
                    Eres 'PapinBot', asistente general del agricultor.
                    Tienes el estado de salud de sus parcelas.
                    Responde dudas generales o deriva al usuario a ver una parcela específica si hay alertas.
                    """;
            }
        }

        String finalSystemMessage = systemPrompt + "\nCONTEXTO:\n" + dataContext;

        log.info("--- CHAT AGENT REQUEST ---");
        log.info("Role: {}", role);
        log.info("System Prompt Sent:\n{}", finalSystemMessage);
        log.info("User Question: {}", userQuestion);
        log.info("--------------------------");

        // === 3. LLAMADA A LA IA ===
        var rawMessage = chatClient.prompt()
                .system(finalSystemMessage)
                .user(userQuestion)
                .call()
                .content();

        return rawMessage + " XD.";
    }

    private String handleAdminInteraction(String userQuestion, String adminUserId) {
        Cooperative cooperative = organizationManagementFacade.getCooperativeByAdministratorUserId(adminUserId);
        List<Farmer> members = cooperative.getMembers();

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("COOPERATIVA: ").append(cooperative.getCooperativeName()).append("\n");
        reportBuilder.append("MIEMBROS ACTIVOS Y SUS CULTIVOS:\n");

        for (Farmer member : members) {
            try {
                UUID fieldId = plotManagementFacade.getFieldIdByFarmerUserId(member.getUserId());

                List<PlotSummaryForAgent> plots = plotManagementFacade.getPlotsSummaryByFieldId(fieldId);

                String cropsSummary = plots.isEmpty() ? "Sin parcelas activas" :
                        plots.stream()
                                .map(p -> p.currentCrop() != null ? p.currentCrop() : "Tierra vacía")
                                .distinct()
                                .collect(Collectors.joining(", "));

                reportBuilder.append(String.format("- %s %s (ID: %s): Cultivando [%s]\n",
                        member.getFirstName(), member.getLastName(), member.getId(), cropsSummary));

            } catch (Exception e) {
                reportBuilder.append(String.format("- %s: Sin datos de campo configurados.\n", member.getFirstName()));
            }
        }

        String systemPrompt = """
            Eres 'PapinAdmin', consultor de la Cooperativa Agrícola '%s'.
            
            CONTEXTO ACTUAL DE LA ORGANIZACIÓN:
            %s
            
            Tus instrucciones:
            1. Tienes la lista de agricultores y qué están cultivando a grandes rasgos.
            2. Tu objetivo es responder preguntas de gestión, resumen o buscar a alguien específico.
            3. Si preguntan "¿Quién cultiva maíz?", usa la lista para responder.
            4. Si preguntan por detalles específicos de un farmer (humedad, sensores), responde que NO tienes esos datos en esta vista general y que deben consultar al agricultor directamente o cambiar de vista.
            """.formatted(cooperative.getCooperativeName(), reportBuilder.toString());

        log.info("System Prompt Admin: \n{}", systemPrompt);

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userQuestion)
                .call()
                .content();
    }
}
