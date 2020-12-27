package com.zetaemmesoft.myhome.controller;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zetaemmesoft.myhome.bean.SensorBean;
import com.zetaemmesoft.myhome.service.SensorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/rest/sensor")
public class SensorController {

    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    SensorService sensorService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "Get sensor list", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<List<SensorBean>> getSensors() {
	List<SensorBean> beans = sensorService.getSensors();
	return new ResponseEntity<List<SensorBean>>(beans, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sensorId}/get", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "Get sensor", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<SensorBean> getSensor(@PathVariable("sensorId") Integer sensorId) {
	return new ResponseEntity<SensorBean>(sensorService.getSensor(sensorId), HttpStatus.OK);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "Set sensor", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<SensorBean> setSensor(@RequestBody SensorBean bean) {
	SensorBean rtn = sensorService.setSensor(bean);
	return new ResponseEntity<SensorBean>(rtn, HttpStatus.OK);
    }

    @RequestMapping(value = "/sets", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "Set sensor", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<ArrayList<SensorBean>> setSensor(@RequestBody ArrayList<SensorBean> list) {

	ArrayList<SensorBean> rtn = new ArrayList<SensorBean>();

	if (list != null && list.size() > 0) {
	    for (SensorBean s : list) {
		rtn.add(sensorService.setSensor(s));
	    }
	}

	return ResponseEntity.ok(rtn);
    }
}
