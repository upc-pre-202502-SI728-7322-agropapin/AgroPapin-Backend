package com.agropapin.backend.irrigationautomation.infrastructure.gateways;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttOutboundGateway {

    void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);

}
