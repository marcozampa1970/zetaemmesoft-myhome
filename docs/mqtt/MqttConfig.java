package com.zetaemmesoft.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zetaemmesoft.monitor.integration.mqtt.MqttManager;

@Configuration
public class MqttConfig {

    @Value("${mqtt.host}")
    private String mqttHost;

    @Bean
    public MqttManager mqttClient() {
	return new MqttManager(mqttHost);
    }
}
