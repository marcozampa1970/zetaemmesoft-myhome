package com.zetaemmesoft.myhome.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetaemmesoft.myhome.bean.SensorBean;
import com.zetaemmesoft.myhome.integration.dto.Sensor;
import com.zetaemmesoft.myhome.integration.repository.ItemTypeRepository;
import com.zetaemmesoft.myhome.integration.repository.SensorRepository;

@Service
public class SensorService {

    private static final Logger logger = LoggerFactory.getLogger(SensorService.class);

    //@Value("${mqtt.enabled}")
    //private String mqttEnabled;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    //@Autowired
    //private MqttManager mqttClient;

    @PostConstruct
    public void init() {
	
    }

    public SensorBean getSensor(Integer sensorId) {

	SensorBean bean = null;

	Sensor dto = sensorRepository.find(sensorId);

	if (dto != null) {

	    bean = new SensorBean();
	    bean.setId(dto.getId());
	    bean.setValue(dto.getValue());

	    if (dto.getTime() != null) {
		bean.setDateTime(new Date(dto.getTime().getTime()));
	    }

	    bean.setUnit(dto.getUnit());
	    bean.setType(itemTypeRepository.find(dto.getType()).getName());
	    bean.setName(dto.getName());
	    bean.setNode(dto.getNode());
	}

	return bean;
    }

    public List<SensorBean> getSensors() {

	List<SensorBean> beans = new ArrayList<SensorBean>();

	List<Sensor> dtos = sensorRepository.findAll();

	for (Sensor dto : dtos) {
	    SensorBean bean = new SensorBean();
	    bean.setId(dto.getId());
	    bean.setValue(dto.getValue());

	    if (dto.getTime() != null) {
		bean.setDateTime(new Date(dto.getTime().getTime()));
	    }

	    bean.setUnit(dto.getUnit());
	    bean.setType(itemTypeRepository.find(dto.getType()).getName());
	    bean.setName(dto.getName());
	    bean.setNode(dto.getNode());

	    beans.add(bean);
	}

	return beans;
    }

    public SensorBean setSensor(SensorBean bean) {

	logger.debug("sensorId: " + bean.getId() + " - value: " + bean.getValue());
	
	if (bean.getNode() != null && bean.getNode().equals("")) {
	    bean.setNode(null);
	}

	Date time = new Date();

	Sensor dto = sensorRepository.find(bean.getId());

	if (dto != null) {
	    dto.setValue(bean.getValue());
	    dto.setTime(new java.sql.Timestamp(time.getTime()));
	    int status = sensorRepository.update(dto);
	} else {
	    dto = new Sensor();	    
	    dto.setId(bean.getId());
	    dto.setValue(bean.getValue());
	    dto.setTime(new java.sql.Timestamp(time.getTime()));
	    dto.setUnit(bean.getUnit());

	    if (bean.getType() != null) {
		dto.setType(itemTypeRepository.find(bean.getType()).getId());
	    }

	    dto.setName(bean.getName());
	    dto.setNode(bean.getNode());	    
	    int status = sensorRepository.insert(dto);
	}

	bean.setDateTime(time);
	bean.setValue(dto.getValue());

	/*
	if ("true".equals(mqttEnabled)) {
	    if (bean.getTopic() != null) {
		mqttClient.send(dto.getTopic(), "sensor_" + String.valueOf(dto.getId()), String.valueOf(dto.getValue()), 1, false);
	    } else {
		logger.warn("Unqualified topic!");
	    }
	}*/

	return bean;
    }

}
