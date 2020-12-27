package com.zetaemmesoft.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.zetaemmesoft.services.integration.dto.User;
import com.zetaemmesoft.services.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import java.util.List;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all/get", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "Get the sensor list.", authorizations = { @Authorization(value = "Bearer") })
    public List<User> findAll() {
	return userService.findAll();
    }
}
