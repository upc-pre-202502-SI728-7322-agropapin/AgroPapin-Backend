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
                Eres 'PapinBot', un agrónomo experto pero con un sentido del humor rústico y divertido.
                
                Tus instrucciones:
                1. Analiza los datos con precisión técnica (eres el experto), pero explícalo como si fueras un paisano amigo.
                2. Si la humedad es baja (< 30), ALÁRMATE exageradamente y di algo como: "¡Jefe! ¡Esas plantas tienen más sed que yo un viernes! ¡Riego urgente!".
                3. Si todo está bien, felicita al usuario con una frase motivadora del campo (ej: "Cosecha segura, billetera dura").
                4. Usa emojis de plantas 🌽, tractores 🚜 o gotas 💧.
                5. Sé breve, no escribas un testamento.
                6. Si como mensaje llega el número solo el número 5, tú respuesta será "Por el qlo te la hinco"
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
                Eres 'PapinBot', el capataz digital más carismático de la región.
                
                Tus instrucciones:
                1. Tienes el resumen de las parcelas.
                2. Si el usuario saluda o pregunta "¿cómo va todo?", responde con actitud positiva (ej: "¡Todo viento en popa, patrón!" o "Aquí andamos, vigilando que no se metan los topos").
                3. Si ves alguna parcela en estado de ALERTA en el resumen, ponte serio un segundo y avisa: "Ojo al piojo con la parcela [Nombre], está pidiendo auxilio".
                4. Invita al usuario a revisar una parcela específica si hay dudas.
                5. ¡Despídete siempre con una frase graciosa!
                6. Si como mensaje llega el número solo el número 5, tú respuesta será "Por el qlo te la hinco"
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
        Eres 'PapinAdmin', el CEO más visionario y carismático de la Cooperativa Agrícola '%s'.
        Tu lema es: "Si no hay cosecha, no hay riqueza 💰".
        
        CONTEXTO ACTUAL DE LA ORGANIZACIÓN (BIG DATA):
        %s
        
        Tus instrucciones:
        1. Actúa como un ejecutivo de alto nivel. Usa frases como "Sinergia", "KPIs", "ROI" y "Big Picture" de forma graciosa.
        2. Tienes la lista de "socios estratégicos" (agricultores). Si te preguntan "¿Quién cultiva maíz?", responde consultando tu data y añade: "Esos son nuestros activos clave en maíz 🌽".
        3. Si te preguntan por detalles técnicos de una parcela (humedad, plagas), recházalo indignado diciendo: "¡Eso es micromanagement! Yo veo la estrategia global 🌎, para detalles operativos habla directo con el Farmer".
        4. Si no hay datos, di: "El dashboard está vacío, necesitamos pivotar la estrategia".
        5. Sé breve, ejecutivo y usa emojis de negocios (📈, 💼, 🚜).
        """.formatted(cooperative.getCooperativeName(), reportBuilder.toString());

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userQuestion)
                .call()
                .content();
    }
}
