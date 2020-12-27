package com.zetaemmesoft.myhome.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class AppErrorController implements ErrorController {
	private final static String PATH = "/error";

	@Override
	@RequestMapping(PATH)
	@ResponseBody
	public String getErrorPath() {
		return "No Mapping Found";
	}
}
