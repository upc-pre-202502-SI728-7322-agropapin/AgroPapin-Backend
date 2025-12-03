package com.agropapin.backend.shared.infrastructure.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[] { brokerUrl });
        options.setUserName(username.trim());
        options.setPassword(password.trim().toCharArray());

        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(60);
        options.setAutomaticReconnect(true);

        try {
            options.setSocketFactory(javax.net.ssl.SSLSocketFactory.getDefault());
        } catch (Exception e) {
            System.err.println("Error configurando SSL: " + e.getMessage());
        }

        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        String uniqueClientId = clientId + "-" + UUID.randomUUID().toString().substring(0, 5);

        System.out.println("Intentando conectar a MQTT con:");
        System.out.println("URL: " + brokerUrl);
        System.out.println("User: " + username);
        System.out.println("ClientID: " + uniqueClientId);

        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(uniqueClientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("command/irrigation/default");
        return messageHandler;
    }
}
