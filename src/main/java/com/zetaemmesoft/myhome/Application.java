package com.zetaemmesoft.myhome;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${jdbc.port}")
    private String jdbcPort;

    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
	logger.debug("init");
    }

    @PreDestroy
    public void stop() {
	logger.debug("stop");
    }
}
