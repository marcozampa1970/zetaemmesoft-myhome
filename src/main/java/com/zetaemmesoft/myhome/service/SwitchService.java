package com.zetaemmesoft.myhome.service;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetaemmesoft.myhome.bean.SwitchBean;
import com.zetaemmesoft.myhome.integration.dto.Switch;
import com.zetaemmesoft.myhome.integration.repository.ItemTypeRepository;
import com.zetaemmesoft.myhome.integration.repository.SwitchRepository;

@Service
public class SwitchService {

    private static final Logger logger = LoggerFactory.getLogger(SwitchService.class);

    //@Value("${mqtt.enabled}")
    //private String mqttEnabled;

    @Autowired
    private SwitchRepository switchRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @PostConstruct
    public void init() {
	
    }

    public SwitchBean getSwitch(Integer switchId) {

	SwitchBean bean = null;

	Switch dto = switchRepository.find(switchId);

	if (dto != null) {

	    bean = new SwitchBean();

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

    public List<SwitchBean> getSwitches() {

	List<SwitchBean> beans = new ArrayList<SwitchBean>();

	List<Switch> dtos = switchRepository.findAll();

	for (Switch dto : dtos) {
	    SwitchBean bean = new SwitchBean();
	    bean.setId(dto.getId());
	    bean.setValue(dto.getValue());

	    if (dto.getTime() != null) {
		bean.setDateTime(new Date(dto.getTime().getTime()));
	    }

	    bean.setUnit(dto.getUnit());
	    bean.setType(itemTypeRepository.find(dto.getType()).getName());
	    bean.setName(dto.getName());
	    bean.setNode(dto.getNode());
	    bean.setTopic(dto.getTopic());

	    beans.add(bean);
	}

	return beans;
    }

    public SwitchBean setSwitch(SwitchBean bean) {

	logger.debug("switchId: " + bean.getId() + " - value: " + bean.getValue());	

	if (bean.getNode() != null && bean.getNode().equals("")) {
	    bean.setNode(null);
	}

	Date time = new Date();

	Switch dto = switchRepository.find(bean.getId());

	if (dto != null) {
	    dto.setValue((bean.getValue() > 0) ? 1 : 0);
	    dto.setTime(new java.sql.Timestamp(time.getTime()));
	    switchRepository.update(dto);
	} else {
	    dto = new Switch();
	    
	    // CREATE NEW SWITCH
	    // ID, NODE, VALUE, UNIT, ITEM_TYPE, TIME, NAME, TOPIC
	    
	    dto.setId(bean.getId());
	    dto.setValue((bean.getValue() > 0) ? 1 : 0);
	    dto.setTime(new java.sql.Timestamp(time.getTime()));
	    dto.setUnit(bean.getUnit());

	    if (bean.getType() != null) {
		dto.setType(itemTypeRepository.find(bean.getType()).getId());
	    }

	    dto.setName(bean.getName());
	    dto.setNode(bean.getNode());
	    switchRepository.insert(dto);
	}

	bean.setDateTime(time);
	bean.setValue(dto.getValue());

	return bean;
    }
}
