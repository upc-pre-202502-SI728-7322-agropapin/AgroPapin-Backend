package com.agropapin.backend.irrigationautomation.interfaces.rest.resources;

import java.util.UUID;

public record ActivateIrrigationResource(UUID actuatorId, int minutes) {
}
