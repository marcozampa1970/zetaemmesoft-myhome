package com.zetaemmesoft.myhome.config;

/*
***************************
https://stackoverflow.com/questions/7580508/getting-chrome-to-accept-self-signed-localhost-certificate
***************************

For localhost only:

Simply paste this in your chrome:

chrome://flags/#allow-insecure-localhost

You should see highlighted text saying: Allow invalid certificates for resources loaded from localhost

Click Enable.

***************************
https://unix.stackexchange.com/questions/60750/does-curl-have-a-no-check-certificate-option-like-wget
***************************
*/

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.zetaemmesoft.myhome.controller"))
		.paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo()).host("localhost").securitySchemes(securitySchemes());
    }

    private static ArrayList<? extends SecurityScheme> securitySchemes() {
	return new ArrayList<SecurityScheme>(Arrays.asList(new ApiKey("Bearer", "Authorization", "header")));
    }

    private ApiInfo apiEndPointsInfo() {
	return new ApiInfoBuilder().title("Zetaemmesoft MyHome REST API").build();
    }

}
