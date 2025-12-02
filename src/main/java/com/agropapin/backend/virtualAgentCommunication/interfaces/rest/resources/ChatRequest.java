package com.agropapin.backend.virtualAgentCommunication.interfaces.rest.resources;

import java.util.UUID;

public record ChatRequest(
        String question,
        UUID plotId,
        UUID fieldId
) {
}
