package com.agropapin.backend.virtualAgentCommunication.application.internal.service;

import com.agropapin.backend.telemetryingestion.application.internal.queryservices.TelemetryQueryServiceImpl;
import com.agropapin.backend.telemetryingestion.domain.interfaces.TelemetryReader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class VirtualAgronomistService {
    private final ChatClient chatClient;
    private final TelemetryQueryServiceImpl telemetryQueryService;
    // private final CropService cropService; // Para obtener info del cultivo

    public VirtualAgronomistService(ChatClient.Builder chatClientBuilder, TelemetryQueryServiceImpl telemetryQueryService) {
        this.chatClient = chatClientBuilder.build();
        this.telemetryQueryService = telemetryQueryService;
    }

    public String askAgent(String userQuestion, String plotId) {

        var metrics = telemetryQueryService.getLatestAggregatedMetrics(plotId);

        String systemPrompt = String.format("""
            Eres un agrónomo experto asistente llamado 'AgroBot'.
            
            CONTEXTO ACTUAL DE LA PARCELA (ID: %s):
            - Datos de sensores (Último promedio): %s
            
            Tus instrucciones:
            1. Analiza los datos de los sensores comparados con la pregunta del usuario.
            2. Si la humedad es baja (menos de 30), recomienda riego.
            3. Sé conciso, amable y profesional.
            4. Si no tienes datos suficientes, dilo.
            """, plotId, metrics.toString());

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userQuestion)
                .call()
                .content();
    }
}
