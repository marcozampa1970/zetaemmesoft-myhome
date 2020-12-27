package com.zetaemmesoft.myhome.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    private static final Logger logger = LoggerFactory.getLogger(DatasourceConfig.class);

    @Bean
    public DataSource dataSource() {
	SimpleDriverDataSource ds = new SimpleDriverDataSource();

	ds.setUrl(jdbcUrl);
	ds.setUsername(jdbcUsername);
	ds.setPassword(jdbcPassword);

	Class<? extends Driver> driverClass = null;

	try {
	    driverClass = (Class<? extends Driver>) Class.forName(jdbcDriver);
	} catch (ClassNotFoundException e) {
	    logger.error(e.getMessage());
	}

	ds.setDriverClass(driverClass);

	/*
	ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
	resourceDatabasePopulator.addScript(new ClassPathResource("db-schema.sql"));
	resourceDatabasePopulator.addScript(new ClassPathResource("db-data.sql"));
	DatabasePopulatorUtils.execute(resourceDatabasePopulator, ds);*/

	return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
	return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
	return new NamedParameterJdbcTemplate(dataSource());
    }

}
