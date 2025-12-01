package com.agropapin.backend.virtualAgentCommunication.interfaces.rest;

import com.agropapin.backend.virtualAgentCommunication.application.internal.service.VirtualAgronomistService;
import com.agropapin.backend.virtualAgentCommunication.interfaces.rest.resources.ChatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentAgronomistController {
    private final VirtualAgronomistService agentService;

    public AgentAgronomistController(VirtualAgronomistService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chatWithAgent(@RequestBody ChatRequest request) {
        // Asumimos que el request trae { "question": "...", "plotId": "..." }
        String response = agentService.askAgent(request.question(), request.plotId());

        return ResponseEntity.ok(Map.of("response", response));
    }
}
