package com.zetaemmesoft.monitor.integration.mqtt;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.zetaemmesoft.monitor.integration.dto.Switch;
import com.zetaemmesoft.monitor.integration.repository.SwitchRepository;

public class MqttManager {

    private static final Logger logger = LoggerFactory.getLogger(MqttManager.class);

    @Autowired
    private SwitchRepository switchRepository;

    private String host;
    private static HashMap<String, IMqttClient> clients;
    MqttConnectOptions options = new MqttConnectOptions();

    public MqttManager(String host) {

	options.setAutomaticReconnect(true);
	options.setCleanSession(true);
	options.setConnectionTimeout(60);

	this.host = host;
	clients = new HashMap<String, IMqttClient>();
    }

    public synchronized void subscribe(String topic, String clientId) {

	IMqttClient client = null;

	try {

	    client = clients.get(clientId);

	    if (client == null) {
		client = new MqttClient(host, clientId);
		clients.put(clientId, client);
	    }

	    if (client != null && !client.isConnected()) {
		client.connect(options);
	    }

	    if (client != null && client.isConnected()) {
		client.subscribe(topic, (t, msg) -> {

		    int switchId = Integer.valueOf(clientId.split("_")[1]);
		    Switch dto = switchRepository.find(switchId);

		    if (dto != null) {
			int payloaddValue = Integer.valueOf(new String(msg.getPayload()));

			if (payloaddValue != dto.getValue()) {
			    dto.setValue(payloaddValue);
			    switchRepository.update(dto);
			}
		    }
		});
	    }
	} catch (MqttException e) {
	    logger.error(e.getMessage());
	}
    }

    @Async
    public synchronized void send(String topic, String clientId, String value, int qos, boolean retain) {

	IMqttClient client = null;

	try {

	    client = clients.get(clientId);

	    if (client == null) {
		client = new MqttClient(host, clientId);
		clients.put(clientId, client);
	    }

	    if (client != null && !client.isConnected()) {
		client.connect(options);
	    }

	    if (client != null && client.isConnected()) {
		MqttMessage mqttMsg = new MqttMessage(value.getBytes());
		mqttMsg.setQos(qos);
		mqttMsg.setRetained(retain);

		client.publish(topic, mqttMsg);
	    }
	} catch (MqttException e) {
	    logger.error(e.getMessage());
	}
    }

    @PreDestroy
    public void destroy() {
	logger.info("destroy active mqtt client");

	Iterator<Entry<String, IMqttClient>> iterator = clients.entrySet().iterator();

	while (iterator.hasNext()) {
	    Map.Entry<String, IMqttClient> entry = iterator.next();
	    IMqttClient client = entry.getValue();

	    if (client.isConnected()) {
		try {
		    client.disconnect();
		    client.close();
		    logger.info(entry.getKey());
		} catch (MqttException e) {
		    logger.error(e.getMessage());
		}
	    }
	}
    }
}
