package com.zetaemmesoft.myhome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zetaemmesoft.myhome.bean.SwitchBean;
import com.zetaemmesoft.myhome.service.SwitchService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/rest/switch")
public class SwitchController {

    @Autowired
    SwitchService switchService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'POWER_USER')")
    @ApiOperation(value = "Get switches", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<List<SwitchBean>> getSwitches() {
	List<SwitchBean> beans = switchService.getSwitches();
	return new ResponseEntity<List<SwitchBean>>(beans, HttpStatus.OK);
    }

    @RequestMapping(value = "/{switchId}/get", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'POWER_USER')")
    @ApiOperation(value = "Get switch", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<SwitchBean> getSwitch(@PathVariable("switchId") Integer switchId) {
	return new ResponseEntity<SwitchBean>(switchService.getSwitch(switchId), HttpStatus.OK);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'POWER_USER')")
    @ApiOperation(value = "Set switch", authorizations = { @Authorization(value = "Bearer") })
    public ResponseEntity<SwitchBean> setSwitch(@RequestBody SwitchBean bean) {
	SwitchBean rtn = switchService.setSwitch(bean);
	return new ResponseEntity<SwitchBean>(rtn, HttpStatus.OK);
    }
}
