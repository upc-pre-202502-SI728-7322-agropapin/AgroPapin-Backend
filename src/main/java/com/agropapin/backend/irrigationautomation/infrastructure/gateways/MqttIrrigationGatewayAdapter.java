package com.agropapin.backend.irrigationautomation.infrastructure.gateways;

import com.agropapin.backend.irrigationautomation.domain.model.gateways.IrrigationMqttGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MqttIrrigationGatewayAdapter implements IrrigationMqttGateway {

    private static final Logger log = LoggerFactory.getLogger(MqttIrrigationGatewayAdapter.class);
    private final MqttOutboundGateway mqttOutboundGateway;

    public MqttIrrigationGatewayAdapter(MqttOutboundGateway mqttOutboundGateway) {
        this.mqttOutboundGateway = mqttOutboundGateway;
    }

    @Override
    public void publishIrrigationCommand(UUID actuatorId, int minutes) {
        String topic = String.format("command/irrigation/%s", actuatorId);
        // The payload can be a simple string, or a more complex JSON object.
        // For this case, sending the number of minutes is enough.
        String payload = String.valueOf(minutes);

        try {
            log.info("Publishing MQTT message to topic '{}' with payload '{}'", topic, payload);
            mqttOutboundGateway.sendToMqtt(payload, topic);
        } catch (Exception e) {
            log.error("Failed to publish MQTT message to topic {}", topic, e);
            // Handle the exception, e.g., by throwing a custom infrastructure exception
        }
    }
}
