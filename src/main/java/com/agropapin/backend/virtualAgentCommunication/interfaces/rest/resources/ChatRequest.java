package com.agropapin.backend.virtualAgentCommunication.interfaces.rest.resources;

public record ChatRequest(
        String question,
        String plotId
) {
}
